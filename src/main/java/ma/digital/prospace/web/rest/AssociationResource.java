package ma.digital.prospace.web.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<AssociationDTO> getAssociation(@PathVariable UUID id) {
        log.debug("REST request to get Association : {}", id);
        AssociationDTO associationDTO = associationService.findOne(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        return ResponseEntity.ok()
                .body(associationDTO);
    }

    @DeleteMapping("/associations/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete Association : {}", id);
        associationService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/association/processAuthenticationStep2")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CompteFSAssociationDTO> processAuthenticationStep2(@RequestParam String compteID, @RequestParam UUID fs,
                                                                             @RequestParam String transactionID) {
        CompteFSAssociationDTO responseDTO = associationService.processAuthenticationStep2(compteID, fs, transactionID);
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
    @GetMapping("/association/checkAuthenticationStep2")
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
    public ResponseEntity<AssociationDTO> createAssociation(@RequestBody AssociationDTO dto) {
        AssociationDTO createdDto = associationService.createAssociation(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }
    @PutMapping("/associations")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<AssociationDTO> updateAssociationStatut(@RequestParam UUID id, @RequestBody String statut) {
        StatutAssociation nouveauStatut;
        try {
            nouveauStatut = StatutAssociation.valueOf(statut);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid statut value");
        }

        AssociationDTO updatedAssociation = associationService.updateStatut(id, nouveauStatut);
        return ResponseEntity.ok(updatedAssociation);
    }


}

