package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.ProcurationService;
import ma.digital.prospace.service.dto.ProcurationDTO;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
/**
 * REST controller for managing {@link ma.digital.prospace.domain.Procuration}.
 */
@RestController
@RequestMapping("/api")
public class ProcurationResource {

    private final Logger log = LoggerFactory.getLogger(ProcurationResource.class);

    private static final String ENTITY_NAME = "procuration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcurationService procurationService;
    private final ProcurationRepository procurationRepository;

    public ProcurationResource(ProcurationService procurationService, ProcurationRepository procurationRepository) {
        this.procurationService = procurationService;
        this.procurationRepository = procurationRepository;
    }
    @PutMapping("/procurations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProcurationDTO> updateProcuration(
            @PathVariable(value = "id", required = false) final UUID id,
            @RequestBody ProcurationDTO procuration
    ) throws URISyntaxException {
        log.debug("REST request to update ProcurationDTO : {}, {}", id, procuration);
        if (procuration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procuration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcurationDTO result = procurationService.update(procuration);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procuration.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /procurations/:id} : Partial updates given fields of an existing procuration, field will ignore if it is null
     *
     * @param id the id of the procuration to save.
     * @param procuration the procuration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procuration,
     * or with status {@code 400 (Bad Request)} if the procuration is not valid,
     * or with status {@code 404 (Not Found)} if the procuration is not found,
     * or with status {@code 500 (Internal Server Error)} if the procuration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/procurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcurationDTO> partialUpdateProcuration(
            @PathVariable(value = "id", required = false) final UUID id,
            @RequestBody ProcurationDTO procuration
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcurationDTO partially : {}, {}", id, procuration);
        if (procuration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procuration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcurationDTO> result = procurationService.partialUpdate(procuration);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procuration.getId().toString())
        );
    }

    /**
     * {@code GET  /procurations} : get all the procurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procurations in body.
     */
    @GetMapping("/procurations")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ProcurationDTO>> getAllProcurations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Procurations");
        Page<ProcurationDTO> page = procurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /procurations/:id} : get the "id" procuration.
     *
     * @param id the id of the procuration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procuration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procurations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProcurationDTO> getProcuration(@PathVariable UUID id) {
        log.debug("REST request to get ProcurationDTO : {}", id);
        Optional<ProcurationDTO> procuration = procurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procuration);
    }
    /**
     * POST  /procurations : Create a new procuration.
     *
     * @param procurationDTO the procurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procurationDTO,
     * or with status 400 (Bad Request) if the procuration has not been created
     */


    @PostMapping("/procurations")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProcurationDTO> createProcuration(@RequestBody ProcurationDTO procurationDTO) {
        try {
            ProcurationDTO result = procurationService.createProcuration(procurationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/procurations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProcurationDTO> acceptProcuration(@PathVariable UUID id) {
        try {
            ProcurationDTO result = procurationService.acceptProcuration(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }





    @DeleteMapping("/procurations/{procurationID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteProcuration(@PathVariable UUID procurationID) {
        procurationService.deleteProcuration(procurationID);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, procurationID.toString())).build();
    }
}