package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import ma.digital.prospace.service.dto.CompteProContactDTO;
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
import org.springframework.web.bind.annotation.*;
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
    private static final Logger auditLogger = LoggerFactory.getLogger("");
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
        String result = compteProService.delete(id);
        switch (result) {
            case "deleted":
                return ResponseEntity.noContent().header("X-comptePro-deletion", "Votre compte professionnel a été supprimé avec succès.").build();
            case "already_deleted":
                return ResponseEntity.status(HttpStatus.GONE).header("X-comptePro-deletion", "Ce compte professionnel a déjà été supprimé précédemment.").build();
            case "not_found":
                return ResponseEntity.notFound().header("X-comptePro-deletion", "Aucun compte professionnel trouvé avec cet identifiant.").build();
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("X-comptePro-deletion", "Erreur interne du serveur lors de la tentative de suppression du compte professionnel.").build();
        }
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
    @GetMapping("/mes-espaces")
    public ResponseEntity<List<CompteProDTO>> getMesEspaces(@RequestParam String userId) {
        List<CompteProDTO> espaces = compteProService.listerMesEspaces(userId);
        if (espaces.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }
        return ResponseEntity.ok().body(espaces);
    }
    @GetMapping("/Not_Used-compte-pros/{id}/contact")
    public ResponseEntity<CompteProContactDTO> getCompteProWithContact(@PathVariable String id) {
        Optional<CompteProContactDTO> compteProContactDTO = compteProService.findCompteProWithContact(id);
        return compteProContactDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
