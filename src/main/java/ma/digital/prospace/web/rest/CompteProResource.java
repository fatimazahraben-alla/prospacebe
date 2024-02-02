package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.service.CompteProService;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.ComptePro}.
 */
@RestController
@RequestMapping("/api")
public class CompteProResource {

    private final Logger log = LoggerFactory.getLogger(CompteProResource.class);

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
     * {@code POST  /compte-pros} : Create a new comptePro.
     *
     * @param comptePro the comptePro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comptePro, or with status {@code 400 (Bad Request)} if the comptePro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compte-pros")
    public ResponseEntity<ComptePro> createComptePro(@Valid @RequestBody ComptePro comptePro) throws URISyntaxException {
        log.debug("REST request to save ComptePro : {}", comptePro);
        if (comptePro.getId() != null) {
            throw new BadRequestAlertException("A new comptePro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComptePro result = compteProService.save(comptePro);
        return ResponseEntity
            .created(new URI("/api/compte-pros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<ComptePro> updateComptePro(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComptePro comptePro
    ) throws URISyntaxException {
        log.debug("REST request to update ComptePro : {}, {}", id, comptePro);
        if (comptePro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comptePro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteProRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComptePro result = compteProService.update(comptePro);
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
    public ResponseEntity<ComptePro> partialUpdateComptePro(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComptePro comptePro
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComptePro partially : {}, {}", id, comptePro);
        if (comptePro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comptePro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteProRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComptePro> result = compteProService.partialUpdate(comptePro);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comptePro.getId().toString())
        );
    }

    /**
     * {@code GET  /compte-pros} : get all the comptePros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comptePros in body.
     */
    @GetMapping("/compte-pros")
    public List<ComptePro> getAllComptePros() {
        log.debug("REST request to get all ComptePros");
        return compteProService.findAll();
    }

    /**
     * {@code GET  /compte-pros/:id} : get the "id" comptePro.
     *
     * @param id the id of the comptePro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comptePro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compte-pros/{id}")
    public ResponseEntity<ComptePro> getComptePro(@PathVariable Long id) {
        log.debug("REST request to get ComptePro : {}", id);
        Optional<ComptePro> comptePro = compteProService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comptePro);
    }

    /**
     * {@code DELETE  /compte-pros/:id} : delete the "id" comptePro.
     *
     * @param id the id of the comptePro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compte-pros/{id}")
    public ResponseEntity<Void> deleteComptePro(@PathVariable Long id) {
        log.debug("REST request to delete ComptePro : {}", id);
        compteProService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
