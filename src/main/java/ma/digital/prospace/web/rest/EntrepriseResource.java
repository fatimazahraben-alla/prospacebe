package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.digital.prospace.service.EntrepriseWSMJService;
import ma.digital.prospace.service.TribunalWSMJService;
import ma.digital.prospace.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.EntrepriseService;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.Entreprise}.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrepriseService entrepriseService;
    @Autowired
    private final TribunalWSMJService tribunalService;
    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseWSMJService entrepriseWSMJService;


    public EntrepriseResource(EntrepriseService entrepriseService, EntrepriseRepository entrepriseRepository, TribunalWSMJService tribunalService,EntrepriseWSMJService entrepriseWSMJService) {
        this.entrepriseService = entrepriseService;
        this.entrepriseRepository = entrepriseRepository;
        this.tribunalService = tribunalService;
        this.entrepriseWSMJService= entrepriseWSMJService;
    }

    /**
     * {@code POST  /entreprises} : Create a new entreprise.
     *
     * @param entrepriseRequest the entreprise to create.
     *
     *
     */
    @PostMapping("/entreprises")
    public void createCompany(@RequestBody EntrepriseRequest2 entrepriseRequest) {
        entrepriseService.createCompany(entrepriseRequest);
    }
    /**
     * {@code PUT  /entreprises/:id} : Updates an existing entreprise.
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseRequest> updateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntrepriseRequest entreprise
    ) throws URISyntaxException {
        log.debug("REST request to update EntrepriseDTO : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntrepriseRequest result = entrepriseService.update(entreprise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entreprise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entreprises/:id} : Partial updates given fields of an existing entreprise, field will ignore if it is null
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 404 (Not Found)} if the entreprise is not found,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entreprises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntrepriseRequest> partialUpdateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntrepriseRequest entreprise
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntrepriseDTO partially : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntrepriseRequest> result = entrepriseService.partialUpdate(entreprise);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entreprise.getId().toString())
        );
    }

    /**
     * {@code GET  /entreprises} : get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreprises in body.
     */
    @GetMapping("/entreprises")
    public ResponseEntity<List<EntrepriseRequest>> getAllEntreprises(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entreprises");
        Page<EntrepriseRequest> page = entrepriseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entreprises/:id} : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entreprise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseRequest> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get EntrepriseDTO : {}", id);
        Optional<EntrepriseRequest> entreprise = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entreprise);
    }

    /**
     * {@code DELETE  /entreprises/:id} : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entreprises/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete EntrepriseDTO : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/tribunaux")
    public ResponseEntity<List<Juridiction>> getListeTribunaux() {
            List<Juridiction> tribunaux = tribunalService.getListeTribunaux();
            return ResponseEntity.ok(tribunaux);

    }
    @GetMapping("/entreprise/{codeJuridiction}/{numRC}")
    public EntrepriseWSMJ getEntrepriseByJuridictionAndNumRC(@PathVariable String codeJuridiction, @PathVariable String numRC) {
        return entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(codeJuridiction, numRC);
    }

    @GetMapping("/dirigeant/{codeJuridiction}/{numRC}")
    public DIRIGEANTDTO getDirigeantBycodeJuridictionAndnumRC(@PathVariable String codeJuridiction, @PathVariable String numRC) {
        return entrepriseWSMJService.getDirigeantBycodeJuridictionAndnumRC(codeJuridiction, numRC);
    }


}
