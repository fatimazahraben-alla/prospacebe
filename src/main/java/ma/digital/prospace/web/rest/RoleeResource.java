package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.RoleeService;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.Rolee}.
 */
@RestController
@RequestMapping("/api")
public class RoleeResource {

    private final Logger log = LoggerFactory.getLogger(RoleeResource.class);

    private static final String ENTITY_NAME = "rolee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleeService roleeService;

    private final RoleeRepository roleeRepository;

    public RoleeResource(RoleeService roleeService, RoleeRepository roleeRepository) {
        this.roleeService = roleeService;
        this.roleeRepository = roleeRepository;
    }

    /**
     * {@code POST  /rolees} : Create a new rolee.
     *
     * @param rolee the rolee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolee, or with status {@code 400 (Bad Request)} if the rolee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rolees")
    public ResponseEntity<Rolee> createRolee(@RequestBody Rolee rolee) throws URISyntaxException {
        log.debug("REST request to save Rolee : {}", rolee);
        if (rolee.getId() != null) {
            throw new BadRequestAlertException("A new rolee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rolee result = roleeService.save(rolee);
        return ResponseEntity
            .created(new URI("/api/rolees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rolees/:id} : Updates an existing rolee.
     *
     * @param id the id of the rolee to save.
     * @param rolee the rolee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolee,
     * or with status {@code 400 (Bad Request)} if the rolee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rolees/{id}")
    public ResponseEntity<Rolee> updateRolee(@PathVariable(value = "id", required = false) final Long id, @RequestBody Rolee rolee)
        throws URISyntaxException {
        log.debug("REST request to update Rolee : {}, {}", id, rolee);
        if (rolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rolee result = roleeService.update(rolee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rolees/:id} : Partial updates given fields of an existing rolee, field will ignore if it is null
     *
     * @param id the id of the rolee to save.
     * @param rolee the rolee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolee,
     * or with status {@code 400 (Bad Request)} if the rolee is not valid,
     * or with status {@code 404 (Not Found)} if the rolee is not found,
     * or with status {@code 500 (Internal Server Error)} if the rolee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rolees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rolee> partialUpdateRolee(@PathVariable(value = "id", required = false) final Long id, @RequestBody Rolee rolee)
        throws URISyntaxException {
        log.debug("REST request to partial update Rolee partially : {}, {}", id, rolee);
        if (rolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rolee> result = roleeService.partialUpdate(rolee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolee.getId().toString())
        );
    }

    /**
     * {@code GET  /rolees} : get all the rolees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolees in body.
     */
    @GetMapping("/rolees")
    public List<Rolee> getAllRolees() {
        log.debug("REST request to get all Rolees");
        return roleeService.findAll();
    }

    /**
     * {@code GET  /rolees/:id} : get the "id" rolee.
     *
     * @param id the id of the rolee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rolees/{id}")
    public ResponseEntity<Rolee> getRolee(@PathVariable Long id) {
        log.debug("REST request to get Rolee : {}", id);
        Optional<Rolee> rolee = roleeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolee);
    }

    /**
     * {@code DELETE  /rolees/:id} : delete the "id" rolee.
     *
     * @param id the id of the rolee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rolees/{id}")
    public ResponseEntity<Void> deleteRolee(@PathVariable Long id) {
        log.debug("REST request to delete Rolee : {}", id);
        roleeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
