package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.ProcurationService;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
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

    /**
     * {@code POST  /procurations} : Create a new procuration.
     *
     * @param procuration the procuration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procuration, or with status {@code 400 (Bad Request)} if the procuration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procurations")
    public ResponseEntity<Procuration> createProcuration(@RequestBody Procuration procuration) throws URISyntaxException {
        log.debug("REST request to save Procuration : {}", procuration);
        if (procuration.getId() != null) {
            throw new BadRequestAlertException("A new procuration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Procuration result = procurationService.save(procuration);
        return ResponseEntity
            .created(new URI("/api/procurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procurations/:id} : Updates an existing procuration.
     *
     * @param id the id of the procuration to save.
     * @param procuration the procuration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procuration,
     * or with status {@code 400 (Bad Request)} if the procuration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procuration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procurations/{id}")
    public ResponseEntity<Procuration> updateProcuration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procuration procuration
    ) throws URISyntaxException {
        log.debug("REST request to update Procuration : {}, {}", id, procuration);
        if (procuration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procuration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Procuration result = procurationService.update(procuration);
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
    public ResponseEntity<Procuration> partialUpdateProcuration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procuration procuration
    ) throws URISyntaxException {
        log.debug("REST request to partial update Procuration partially : {}, {}", id, procuration);
        if (procuration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procuration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Procuration> result = procurationService.partialUpdate(procuration);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procuration.getId().toString())
        );
    }

    /**
     * {@code GET  /procurations} : get all the procurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procurations in body.
     */
    @GetMapping("/procurations")
    public List<Procuration> getAllProcurations() {
        log.debug("REST request to get all Procurations");
        return procurationService.findAll();
    }

    /**
     * {@code GET  /procurations/:id} : get the "id" procuration.
     *
     * @param id the id of the procuration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procuration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procurations/{id}")
    public ResponseEntity<Procuration> getProcuration(@PathVariable Long id) {
        log.debug("REST request to get Procuration : {}", id);
        Optional<Procuration> procuration = procurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procuration);
    }

    /**
     * {@code DELETE  /procurations/:id} : delete the "id" procuration.
     *
     * @param id the id of the procuration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procurations/{id}")
    public ResponseEntity<Void> deleteProcuration(@PathVariable Long id) {
        log.debug("REST request to delete Procuration : {}", id);
        procurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}