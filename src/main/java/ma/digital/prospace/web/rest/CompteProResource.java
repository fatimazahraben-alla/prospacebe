package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import jakarta.persistence.EntityExistsException;
import ma.digital.prospace.service.dto.ContactDTO;
import ma.digital.prospace.service.dto.MobileRegistrationDTO;
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

import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.service.CompteProService;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.ComptePro}.
 */
@RestController
@RequestMapping("/api")
public class CompteProResource {

    private final Logger log = LoggerFactory.getLogger(CompteProResource.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("ma.digital.prospace.audit2");
    private static final String ENTITY_NAME = "comptePro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteProService compteProService;

    private final CompteProRepository compteProRepository;

    public CompteProResource(CompteProService compteProService, CompteProRepository compteProRepository) {
        this.compteProService = compteProService;
        this.compteProRepository = compteProRepository;
    }



    /**
     * {@code PUT  /compte-pros/:id} : Updates an existing comptePro.
     *
     * @param id the id of the comptePro to save.
     * @param comptePro the comptePro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comptePro,
     * or with status {@code 400 (Bad Request)} if the comptePro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comptePro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compte-pros/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CompteProDTO> updateComptePro(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CompteProDTO comptePro
    ) throws URISyntaxException {
        log.debug("REST request to update CompteProDTO : {}, {}", id, comptePro);
        if (comptePro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comptePro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteProRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompteProDTO result = compteProService.update(comptePro);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comptePro.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compte-pros/:id} : Partial updates given fields of an existing comptePro, field will ignore if it is null
     *
     * @param id the id of the comptePro to save.
     * @param comptePro the comptePro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comptePro,
     * or with status {@code 400 (Bad Request)} if the comptePro is not valid,
     * or with status {@code 404 (Not Found)} if the comptePro is not found,
     * or with status {@code 500 (Internal Server Error)} if the comptePro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compte-pros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompteProDTO> partialUpdateComptePro(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CompteProDTO comptePro
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompteProDTO partially : {}, {}", id, comptePro);
        if (comptePro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comptePro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteProRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompteProDTO> result = compteProService.partialUpdate(comptePro);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comptePro.getId().toString())
        );
    }

    /**
     * {@code GET  /compte-pros} : get all the comptePros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comptePros in body.
     */
    @GetMapping("/compte-pros")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CompteProDTO>> getAllComptePros(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ComptePros");
        Page<CompteProDTO> page = compteProService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compte-pros/:id} : get the "id" comptePro.
     *
     * @param id the id of the comptePro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comptePro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compte-pros/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CompteProDTO> getComptePro(@PathVariable String id) {
        log.debug("REST request to get CompteProDTO : {}", id);
        Optional<CompteProDTO> comptePro = compteProService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comptePro);
    }

    /**
     * {@code DELETE  /compte-pros/:id} : delete the "id" comptePro.
     *
     * @param id the id of the comptePro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compte-pros/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteComptePro(@PathVariable String id) {
        log.debug("REST request to delete CompteProDTO : {}", id);
        compteProService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @PostMapping("/compte-pros")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CompteProDTO> createAccount(@RequestBody MobileRegistrationDTO registrationDTO) {
        try {
            CompteProDTO newComptePro = compteProService.createAccountWithMobileRegistration(registrationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newComptePro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
