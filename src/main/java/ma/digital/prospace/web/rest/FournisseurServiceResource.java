package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.repository.FournisseurServiceRepository;
import ma.digital.prospace.service.FournisseurServiceService;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.FournisseurService}.
 */
@RestController
@RequestMapping("/api")
public class FournisseurServiceResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurServiceResource.class);

    private static final String ENTITY_NAME = "fournisseurService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FournisseurServiceService fournisseurServiceService;

    private final FournisseurServiceRepository fournisseurServiceRepository;

    public FournisseurServiceResource(
        FournisseurServiceService fournisseurServiceService,
        FournisseurServiceRepository fournisseurServiceRepository
    ) {
        this.fournisseurServiceService = fournisseurServiceService;
        this.fournisseurServiceRepository = fournisseurServiceRepository;
    }

    /**
     * {@code POST  /fournisseur-services} : Create a new fournisseurService.
     *
     * @param fournisseurService the fournisseurService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fournisseurService, or with status {@code 400 (Bad Request)} if the fournisseurService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fournisseur-services")
    public ResponseEntity<FournisseurService> createFournisseurService(@RequestBody FournisseurService fournisseurService)
        throws URISyntaxException {
        log.debug("REST request to save FournisseurService : {}", fournisseurService);
        if (fournisseurService.getId() != null) {
            throw new BadRequestAlertException("A new fournisseurService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FournisseurService result = fournisseurServiceService.save(fournisseurService);
        return ResponseEntity
            .created(new URI("/api/fournisseur-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fournisseur-services/:id} : Updates an existing fournisseurService.
     *
     * @param id the id of the fournisseurService to save.
     * @param fournisseurService the fournisseurService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurService,
     * or with status {@code 400 (Bad Request)} if the fournisseurService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fournisseur-services/{id}")
    public ResponseEntity<FournisseurService> updateFournisseurService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurService fournisseurService
    ) throws URISyntaxException {
        log.debug("REST request to update FournisseurService : {}, {}", id, fournisseurService);
        if (fournisseurService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FournisseurService result = fournisseurServiceService.update(fournisseurService);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fournisseurService.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fournisseur-services/:id} : Partial updates given fields of an existing fournisseurService, field will ignore if it is null
     *
     * @param id the id of the fournisseurService to save.
     * @param fournisseurService the fournisseurService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurService,
     * or with status {@code 400 (Bad Request)} if the fournisseurService is not valid,
     * or with status {@code 404 (Not Found)} if the fournisseurService is not found,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fournisseur-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FournisseurService> partialUpdateFournisseurService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FournisseurService fournisseurService
    ) throws URISyntaxException {
        log.debug("REST request to partial update FournisseurService partially : {}, {}", id, fournisseurService);
        if (fournisseurService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FournisseurService> result = fournisseurServiceService.partialUpdate(fournisseurService);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fournisseurService.getId().toString())
        );
    }

    /**
     * {@code GET  /fournisseur-services} : get all the fournisseurServices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournisseurServices in body.
     */
    @GetMapping("/fournisseur-services")
    public List<FournisseurService> getAllFournisseurServices() {
        log.debug("REST request to get all FournisseurServices");
        return fournisseurServiceService.findAll();
    }

    /**
     * {@code GET  /fournisseur-services/:id} : get the "id" fournisseurService.
     *
     * @param id the id of the fournisseurService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournisseurService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fournisseur-services/{id}")
    public ResponseEntity<FournisseurService> getFournisseurService(@PathVariable Long id) {
        log.debug("REST request to get FournisseurService : {}", id);
        Optional<FournisseurService> fournisseurService = fournisseurServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournisseurService);
    }

    /**
     * {@code DELETE  /fournisseur-services/:id} : delete the "id" fournisseurService.
     *
     * @param id the id of the fournisseurService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fournisseur-services/{id}")
    public ResponseEntity<Void> deleteFournisseurService(@PathVariable Long id) {
        log.debug("REST request to delete FournisseurService : {}", id);
        fournisseurServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
