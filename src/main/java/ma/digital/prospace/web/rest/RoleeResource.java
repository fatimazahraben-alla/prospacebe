package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.RoleeService;
import ma.digital.prospace.service.dto.RoleeDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
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
    public ResponseEntity<RoleeDTO> createRolee(@RequestBody RoleeDTO rolee) throws URISyntaxException {
        log.debug("REST request to save RoleeDTO : {}", rolee);
        if (rolee.getId() != null) {
            throw new BadRequestAlertException("A new rolee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleeDTO result = roleeService.save(rolee);
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
    public ResponseEntity<RoleeDTO> updateRolee(@PathVariable(value = "id", required = false) final Long id, @RequestBody RoleeDTO rolee)
        throws URISyntaxException {
        log.debug("REST request to update RoleeDTO : {}, {}", id, rolee);
        if (rolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleeDTO result = roleeService.update(rolee);
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
    public ResponseEntity<RoleeDTO> partialUpdateRolee(@PathVariable(value = "id", required = false) final Long id, @RequestBody RoleeDTO rolee)
        throws URISyntaxException {
        log.debug("REST request to partial update RoleeDTO partially : {}, {}", id, rolee);
        if (rolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleeDTO> result = roleeService.partialUpdate(rolee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolee.getId().toString())
        );
    }

    /**
     * {@code GET  /rolees} : get all the rolees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolees in body.
     */
    @GetMapping("/rolees")
    public ResponseEntity<List<RoleeDTO>> getAllRolees(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Rolees");
        Page<RoleeDTO> page = roleeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rolees/:id} : get the "id" rolee.
     *
     * @param id the id of the rolee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rolees/{id}")
    public ResponseEntity<RoleeDTO> getRolee(@PathVariable Long id) {
        log.debug("REST request to get RoleeDTO : {}", id);
        Optional<RoleeDTO> rolee = roleeService.findOne(id);
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
        log.debug("REST request to delete RoleeDTO : {}", id);
        roleeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
