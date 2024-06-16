package ma.digital.prospace.web.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Session;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.AssociationService;
import ma.digital.prospace.service.dto.*;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import ma.digital.prospace.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
@RestController
@RequestMapping("/api")
public class AssociationResource {

    private final Logger log = LoggerFactory.getLogger(AssociationResource.class);
    private static final String ENTITY_NAME = "association";
    private  AssociationRepository associationRepository;
    private  AssociationMapper associationMapper;
    private  SessionRepository sessionRepository;
    private  CompteProRepository compteProRepository;
    private  EntrepriseRepository entrepriseRepository;
    private  RoleeRepository roleeRepository;
    private  AssociationService associationService;

    public AssociationResource(AssociationService associationService, CompteProRepository compteProRepository, EntrepriseRepository entrepriseRepository, RoleeRepository roleeRepository) {
        this.associationService = associationService;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleeRepository = roleeRepository;
    }

    @GetMapping("/associations")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Page<AssociationDTO>> getAllAssociations(Pageable pageable) {
        log.debug("REST request to get a page of Associations");
        Page<AssociationDTO> page = associationService.findAll(pageable);
        return ResponseEntity.ok()
                .body(page);
    }
    @GetMapping("/associations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAssociation(@PathVariable UUID id) {
        log.debug("REST request to get Association : {}", id);
        try {
            AssociationDTO associationDTO = associationService.findOne(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Association not found"));
            return ResponseEntity.ok().body(associationDTO);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            log.error("Error retrieving association with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/association/processAuthenticationStep2")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CompteFSAssociationDTO> processAuthenticationStep2(@RequestParam String compteID, @RequestParam String fs,
                                                                             @RequestParam String transactionID) {
        CompteFSAssociationDTO responseDTO = null;
        try {
            responseDTO = associationService.processAuthenticationStep2(compteID, fs, transactionID);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (responseDTO != null) {
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/association/pushCompteEntreprise")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> pushCompteEntreprise(@RequestBody CompteEntrepriseDTO compteEntrepriseDTO) {
        try {
            associationService.pushCompteEntreprise(compteEntrepriseDTO);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/association/NotUsedcheckAuthenticationStep2")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Session.Status> checkAuthenticationStep2(@RequestParam String transactionId) {
        try {
            Session.Status sessionStatus = associationService.checkAuthenticationStep2(transactionId);
            return ResponseEntity.ok(sessionStatus);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/associations")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> createAssociationWithNotification(
            @RequestParam String compteID,
            @RequestParam String compteInitiateurID,
            @RequestParam String destinataireID,
            @RequestParam String entrepriseID,
            @RequestParam UUID roleID,
            @RequestParam String prenomInitiateur,
            @RequestParam String nomInitiateur,
            @RequestParam String nomEntreprise) {

        log.debug("Requête REST pour créer une association avec notification pour compteID: {}, compteInitiateurID: {}, destinataireID: {}, entrepriseID: {}, roleID: {}, prenomInitiateur: {}, nomInitiateur: {}, nomEntreprise: {}",
                compteID, compteInitiateurID, destinataireID, entrepriseID, roleID, prenomInitiateur, nomInitiateur, nomEntreprise);

        try {
            Optional<Object> resultOpt = associationService.createAssociationWithNotification(
                    compteID, compteInitiateurID, destinataireID, entrepriseID, roleID, prenomInitiateur, nomInitiateur, nomEntreprise);

            if (resultOpt.isPresent()) {
                Object result = resultOpt.get();
                if (result instanceof ErrorResponse) {
                    ErrorResponse errorResponse = (ErrorResponse) result;
                    log.warn("Erreur lors de la création de l'association : {}", errorResponse.getMessage());
                    return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getStatusCode())).body(errorResponse);
                }
                AssociationDTO associationDTO = (AssociationDTO) result;
                URI location = URI.create(String.format("/api/associations/%s", associationDTO.getId()));
                return ResponseEntity.created(location).body(associationDTO);
            } else {
                log.error("Erreur inattendue lors de la création de l'association.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Erreur inattendue."));
            }
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'association avec notification", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Erreur interne du serveur : " + e.getMessage()));
        }
    }


    @PatchMapping("/associations/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateAssociationStatut(
            @PathVariable UUID id,
            @RequestParam StatutAssociation statut,
            @RequestBody NomPrenomEntrepriseDTO nomPrenomEntrepriseDTO) {
        log.debug("REST request to update Association status: {}, {}", id, statut);

        try {
            AssociationDTO updatedAssociation = associationService.updateAssociationStatut(
                    id, statut, nomPrenomEntrepriseDTO.getNom(), nomPrenomEntrepriseDTO.getPrenom());
            return ResponseEntity.ok(updatedAssociation);
        } catch (BadRequestAlertException e) {
            log.error("Error updating association status with cause = '{}' and exception = '{}'", e.getCause(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Error updating association status: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Exception in updateAssociationStatut() with cause = '{}' and exception = '{}'", e.getCause(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "An error occurred while updating association status"));
        }
    }

    @DeleteMapping("/associations/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteAssociation(
            @PathVariable UUID id,
            @RequestBody NomPrenomEntrepriseDTO nomPrenomEntrepriseDTO) {
        log.debug("REST request to delete Association: {}, {}", id, nomPrenomEntrepriseDTO);

        try {
            associationService.deleteAssociation(id, nomPrenomEntrepriseDTO);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            log.error("Exception in deleteAssociation() with cause = '{}' and exception = '{}'", e.getCause(), e.getMessage());
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            log.error("Exception in deleteAssociation() with cause = '{}' and exception = '{}'", e.getCause(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "An error occurred while deleting association"));
        }
    }
    @GetMapping("/associations/roles")
    public ResponseEntity<?> getRolesByCompteProAndEntreprise(@RequestParam String compteProId, @RequestParam String entrepriseId) {
        if ((!compteProRepository.existsById(compteProId)) ||  (!entrepriseRepository.existsById(entrepriseId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ComptePro or Entreprise not found");
        }
        List<String> roles = associationService.getRolesByCompteProAndEntreprise(compteProId, entrepriseId);
        if (roles.isEmpty()) {
            return new ResponseEntity<>("No roles found for the provided ComptePro and Entreprise", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @GetMapping("/associations/EntreprisesRoles")
    public ResponseEntity<List<AssociationDTO>> getEntrepriseRole(@RequestParam String fs, @RequestParam String compteProId) {
        List<AssociationDTO> associations = associationService.getEntrepriseRole(fs, compteProId);
        return ResponseEntity.ok().body(associations);
    }
    @GetMapping("/associations/espacePro/{compteID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<AssociationDTO>> getAssociationsCreatedByAccountAndDelegates(@PathVariable String compteID) {
        log.debug("REST request to get Associations created by account and its delegates : {}", compteID);
        List<AssociationDTO> associations = associationService.findAssociationsCreatedByAccountAndDelegates(compteID);
        return ResponseEntity.ok().body(associations);
    }
}