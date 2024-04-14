package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.digital.prospace.domain.FournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import ma.digital.prospace.repository.FournisseurServiceRepository;
import ma.digital.prospace.service.FournisseurServiceService;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FournisseurServiceDTO> partialUpdateFournisseurService(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody FournisseurServiceDTO fournisseurService
    ) throws URISyntaxException {
        log.debug("REST request to partial update FournisseurServiceDTO partially : {}, {}", id, fournisseurService);
        if (fournisseurService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fournisseurService.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fournisseurServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FournisseurServiceDTO> result = fournisseurServiceService.partialUpdate(fournisseurService);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fournisseurService.getId().toString())
        );
    }

    /**
     * {@code GET  /fournisseur-services} : get all the fournisseurServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournisseurServices in body.
     */
    @GetMapping("/fournisseur-services")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<FournisseurServiceDTO>> getAllFournisseurServices(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FournisseurServices");
        Page<FournisseurServiceDTO> page = fournisseurServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fournisseur-services/:id} : get the "id" fournisseurService.
     *
     * @param id the id of the fournisseurService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournisseurService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fournisseur-services/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FournisseurServiceDTO> getFournisseurService(@PathVariable Long id) {
        log.debug("REST request to get FournisseurServiceDTO : {}", id);
        Optional<FournisseurServiceDTO> fournisseurService = fournisseurServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournisseurService);
    }

    /**
     * {@code DELETE  /fournisseur-services/:id} : delete the "id" fournisseurService.
     *
     * @param id the id of the fournisseurService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fournisseur-services/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteFournisseurService(@PathVariable Long id) {
        log.debug("REST request to delete FournisseurServiceDTO : {}", id);
        fournisseurServiceService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
    @PostMapping("/fournisseur-services")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FournisseurServiceDTO> createFournisseurService(@RequestBody FournisseurServiceDTO fournisseurServiceDTO) {
        FournisseurServiceDTO createdFournisseurService = fournisseurServiceService.save(fournisseurServiceDTO);
        return new ResponseEntity<>(createdFournisseurService, HttpStatus.CREATED);
    }

    @PutMapping("/fournisseur-services/{fournisseurID}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FournisseurServiceDTO> updateFournisseurService(@PathVariable Long fournisseurID, @RequestBody FournisseurServiceDTO fournisseurServiceDTO) {
        fournisseurServiceDTO.setId(fournisseurID);
        FournisseurServiceDTO updatedFournisseurService = fournisseurServiceService.update(fournisseurServiceDTO);
        return new ResponseEntity<>(updatedFournisseurService, HttpStatus.OK);
    }
}