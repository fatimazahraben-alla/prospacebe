package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ma.digital.prospace.service.dto.ResponseauthenticationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.service.AssociationService;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.Association}.
 */
@RestController
@RequestMapping("/api")
public class AssociationResource {

    private final Logger log = LoggerFactory.getLogger(AssociationResource.class);

    private static final String ENTITY_NAME = "association";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociationService associationService;

    private final AssociationRepository associationRepository;

    public AssociationResource(AssociationService associationService, AssociationRepository associationRepository) {
        this.associationService = associationService;
        this.associationRepository = associationRepository;
    }

    /**
     * {@code POST  /associations} : Create a new association.
     *
     * @param association the association to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new association, or with status {@code 400 (Bad Request)} if the association has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associations")
    public ResponseEntity<AssociationDTO> createAssociation(@Valid @RequestBody AssociationDTO association) throws URISyntaxException {
        log.debug("REST request to save AssociationDTO: {}", association);
        if (association.getId() != null) {
            throw new BadRequestAlertException("A new association cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssociationDTO result = associationService.save(association);
        return ResponseEntity
            .created(new URI("/api/associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /associations/:id} : Updates an existing association.
     *
     * @param id the id of the association to save.
     * @param association the association to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated association,
     * or with status {@code 400 (Bad Request)} if the association is not valid,
     * or with status {@code 500 (Internal Server Error)} if the association couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associations/{id}")
    public ResponseEntity<AssociationDTO> updateAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssociationDTO association
    ) throws URISyntaxException {
        log.debug("REST request to update AssociationDTO: {}, {}", id, association);
        if (association.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, association.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssociationDTO result = associationService.update(association);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, association.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /associations/:id} : Partial updates given fields of an existing association, field will ignore if it is null
     *
     * @param id the id of the association to save.
     * @param association the association to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated association,
     * or with status {@code 400 (Bad Request)} if the association is not valid,
     * or with status {@code 404 (Not Found)} if the association is not found,
     * or with status {@code 500 (Internal Server Error)} if the association couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/associations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssociationDTO> partialUpdateAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssociationDTO association
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssociationDTOpartially : {}, {}", id, association);
        if (association.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, association.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssociationDTO> result = associationService.partialUpdate(association);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, association.getId().toString())
        );
    }

    /**
     * {@code GET  /associations} : get all the associations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of associations in body.
     */
    @GetMapping("/associations")
    public ResponseEntity<List<AssociationDTO>> getAllAssociations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Associations");
        Page<AssociationDTO> page = associationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /associations/:id} : get the "id" association.
     *
     * @param id the id of the association to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the association, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associations/{id}")
    public ResponseEntity<AssociationDTO> getAssociation(@PathVariable Long id) {
        log.debug("REST request to get AssociationDTO: {}", id);
        Optional<AssociationDTO> association = associationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(association);
    }

    /**
     * {@code DELETE  /associations/:id} : delete the "id" association.
     *
     * @param id the id of the association to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associations/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long id) {
        log.debug("REST request to delete AssociationDTO: {}", id);
        associationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @GetMapping("/association/processAuthenticationStep2")
    public ResponseEntity<ResponseauthenticationDTO> processAuthenticationStep2(@RequestParam Long compteID, @RequestParam Long fs) {
        return associationService.processAuthenticationStep2(compteID, fs);
    }

}
