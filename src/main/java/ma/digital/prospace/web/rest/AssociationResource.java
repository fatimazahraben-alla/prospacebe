package ma.digital.prospace.web.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Session;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.AssociationService;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.CompteFSAssociationDTO;
import ma.digital.prospace.service.dto.RoleRequestDTO;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
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
    private final AssociationService associationService;

    public AssociationResource(AssociationService associationService) {
        this.associationService = associationService;
    }

    /*@PostMapping("/associations")
    public ResponseEntity<AssociationDTO> createAssociation(@Valid @RequestBody AssociationDTO associationDTO) throws URISyntaxException {
        log.debug("REST request to save Association : {}", associationDTO);
        if (associationDTO.getId() != null) {
            throw new IllegalArgumentException("A new association cannot already have an ID");
        }
        AssociationDTO result = associationService.save(associationDTO);
        return ResponseEntity.created(new URI("/api/associations/" + result.getId()))
                .body(result);
    }*/

    /*@PutMapping("/associations")
    public ResponseEntity<AssociationDTO> updateAssociation(@Valid @RequestBody AssociationDTO associationDTO) {
        log.debug("REST request to update Association : {}", associationDTO);
        if (associationDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        AssociationDTO result = associationService.update(associationDTO);
        return ResponseEntity.ok()
                .body(result);
    }*/

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
    @DeleteMapping("/associations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete Association : {}", id);
        associationService.delete(id);
        return ResponseEntity.noContent()
                .build();
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> createAssociation(@RequestBody AssociationDTO associationDTO) {
        try {
            AssociationDTO result = associationService.createAssociation(associationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Collections.singletonMap("error", e.getReason()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Data integrity violation: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }
    @PutMapping("/associations")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<AssociationDTO> updateAssociationStatut(@RequestParam UUID id, @RequestParam String statut) {
        StatutAssociation nouveauStatut;
        try {
            nouveauStatut = StatutAssociation.valueOf(statut.toUpperCase()); // Ensure case insensitivity
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid statut value");
        }

        try {
            AssociationDTO updatedAssociation = associationService.updateStatut(id, nouveauStatut);
            return ResponseEntity.ok(updatedAssociation);
        } catch (ResponseStatusException | FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/associations/demande-role")
    public ResponseEntity<AssociationDTO> demanderRole(@RequestBody RoleRequestDTO roleRequest) {
        try {
            AssociationDTO result = associationService.demanderRole(roleRequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(result.getId()).toUri();
            return ResponseEntity.created(location).body(result);
        } catch (Exception e) {
            // Log the exception details here
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/associations/roles")
    public ResponseEntity<?> getRolesByCompteProAndEntreprise(@RequestParam String compteProId, @RequestParam UUID entrepriseId) {
        List<String> roles = associationService.getRolesByCompteProAndEntreprise(compteProId, entrepriseId);
        if (roles.isEmpty()) {
            return new ResponseEntity<>("No roles found for the provided ComptePro and Entreprise", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}

