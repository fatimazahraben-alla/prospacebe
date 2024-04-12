package ma.digital.prospace.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;


import ma.digital.prospace.service.EntrepriseService;
import ma.digital.prospace.service.EntrepriseWSMJService;
import ma.digital.prospace.service.TribunalWSMJService;
import ma.digital.prospace.service.UserService;
import ma.digital.prospace.service.dto.*;
import ma.digital.prospace.web.rest.errors.CustomException;
import ma.digital.prospace.web.rest.errors.EntrepriseBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.digital.prospace.domain.Entreprise}.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private JwtDecoder jwtDecoder;

    private final EntrepriseService entrepriseService;
    @Autowired
    private final TribunalWSMJService tribunalWSMJService;
    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseWSMJService entrepriseWSMJService;

    private final UserService userService;

    public EntrepriseResource(EntrepriseService entrepriseService, EntrepriseRepository entrepriseRepository, TribunalWSMJService tribunalWSMJService, EntrepriseWSMJService entrepriseWSMJService, UserService userService) {
        this.entrepriseService = entrepriseService;
        this.entrepriseRepository = entrepriseRepository;
        this.tribunalWSMJService = tribunalWSMJService;
        this.entrepriseWSMJService = entrepriseWSMJService;
        this.userService = userService;
    }

    /**
     * POST  /entreprises : Create a new entreprise.
     *
     * @param entrepriseRequest the entreprise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entreprise,
     * or with status 400 (Bad Request) if the entreprise has already an ID
     */
    @PostMapping("/entreprises")
    @PreAuthorize("hasAuthority('ROLE_GESTIONNAIREESPACE')")
    public ResponseEntity<?> createCompany(@RequestBody EntrepriseRequest2 entrepriseRequest,AbstractAuthenticationToken authToken) {
        try {
            entrepriseService.createCompany(entrepriseRequest,authToken);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntrepriseBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request message");
        }
    }

    @GetMapping("/isCurrentUser")
    public boolean isCurrentUser(@RequestParam String accountId) {
        return entrepriseService.isCurrentUser(accountId);
    }

    @GetMapping("/user-info")
    public String getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            // Faites quelque chose avec le principal, par exemple l'imprimer
            System.out.println("Principal de l'utilisateur : " + principal.toString());
            return principal.toString();
        } else {
            System.out.println("Utilisateur non authentifié.");
        }
        return null;
    }

    @GetMapping("/users/{accountId}")
    public ResponseEntity<Boolean> checkUserIdMatchAccount(@PathVariable String accountId, AbstractAuthenticationToken authToken) {
        boolean isMatching = entrepriseService.isUserIdMatchingAccount(accountId, authToken);
        return ResponseEntity.ok(isMatching);
    }

    /**
     * {@code PUT  /entreprises/:id} : Updates an existing entreprise.
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseDTO> updateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntrepriseDTO entreprise
    ) throws URISyntaxException {
        log.debug("REST request to update EntrepriseDTO : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntrepriseDTO result = entrepriseService.update(entreprise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entreprise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entreprises/:id} : Partial updates given fields of an existing entreprise, field will ignore if it is null
     *
     * @param id the id of the entreprise to save.
     * @param entreprise the entreprise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreprise,
     * or with status {@code 400 (Bad Request)} if the entreprise is not valid,
     * or with status {@code 404 (Not Found)} if the entreprise is not found,
     * or with status {@code 500 (Internal Server Error)} if the entreprise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entreprises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntrepriseDTO> partialUpdateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntrepriseDTO entreprise
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntrepriseDTO partially : {}, {}", id, entreprise);
        if (entreprise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreprise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntrepriseDTO> result = entrepriseService.partialUpdate(entreprise);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entreprise.getId().toString())
        );
    }

    /**
     * {@code GET  /entreprises} : get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreprises in body.
     */
    @GetMapping("/entreprises")
    public ResponseEntity<List<EntrepriseDTO>> getAllEntreprises(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entreprises");
        Page<EntrepriseDTO> page = entrepriseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entreprises/:id} : get the "id" entreprise.
     *
     * @param id the id of the entreprise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entreprise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseDTO> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get EntrepriseDTO : {}", id);
        Optional<EntrepriseDTO> entreprise = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entreprise);
    }

    /**
     * {@code DELETE  /entreprises/:id} : delete the "id" entreprise.
     *
     * @param id the id of the entreprise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entreprises/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete EntrepriseDTO : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("entreprise/tribunaux")
    public ResponseEntity<List<Juridiction>> getListeTribunaux() {
        List<Juridiction> tribunaux = tribunalWSMJService.getListeTribunaux();
        return ResponseEntity.ok(tribunaux);

    }
    @GetMapping("/entreprise/{codeJuridiction}/{numRC}")
    public EntrepriseWSMJ getEntrepriseByJuridictionAndNumRC(@PathVariable String codeJuridiction, @PathVariable String numRC) {
        return entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(codeJuridiction, numRC);
    }

    @GetMapping("/dirigeant/{codeJuridiction}/{numRC}")
    public DIRIGEANTDTO getDirigeantBycodeJuridictionAndnumRC(@PathVariable String codeJuridiction, @PathVariable String numRC) {
        return entrepriseWSMJService.getDirigeantBycodeJuridictionAndnumRC(codeJuridiction, numRC);
    }
    @GetMapping("/Personnepysique/{codeJuridiction}/{numRC}")
    public PersonnephysiqueDTO getBycodeJuridictionAndnumRC(@PathVariable String codeJuridiction, @PathVariable String numRC) {
        return entrepriseWSMJService.getBycodeJuridictionAndnumRC(codeJuridiction, numRC);
    }


}
