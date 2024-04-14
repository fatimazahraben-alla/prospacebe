package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ma.digital.prospace.domain.Rolee;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.RoleeService;
import ma.digital.prospace.service.dto.RoleeDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRolee(@PathVariable Long id) {
        log.debug("REST request to delete RoleeDTO : {}", id);
        roleeService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RoleeDTO> createRolee(@Valid @RequestBody RoleeDTO roleeDTO) {
        RoleeDTO createdRolee = roleeService.createRolee(roleeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRolee);
    }

    @PutMapping("/roles/{roleId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RoleeDTO> updateRolee(@PathVariable Long roleId, @Valid @RequestBody RoleeDTO roleeDTO) {
        roleeDTO.setId(roleId); // Set the ID from the path variable to the DTO
        RoleeDTO updatedRolee = roleeService.updateRolee(roleeDTO);
        return ResponseEntity.ok().body(updatedRolee);
    }
}