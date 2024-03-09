package ma.digital.prospace.web.rest;
import ma.digital.prospace.service.AssociationService;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.AuthenticationResponse;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.web.rest.errors.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class AssociationResource {

    private final Logger log = LoggerFactory.getLogger(AssociationResource.class);
    private static final String ENTITY_NAME = "association";
    private final AssociationService associationService;

    public AssociationResource(AssociationService associationService) {
        this.associationService = associationService;
    }

    @PostMapping("/associations")
    public ResponseEntity<AssociationDTO> createAssociation(@Valid @RequestBody AssociationDTO associationDTO) throws URISyntaxException {
        log.debug("REST request to save Association : {}", associationDTO);
        if (associationDTO.getId() != null) {
            throw new IllegalArgumentException("A new association cannot already have an ID");
        }
        AssociationDTO result = associationService.save(associationDTO);
        return ResponseEntity.created(new URI("/api/associations/" + result.getId()))
                .body(result);
    }

    @PutMapping("/associations")
    public ResponseEntity<AssociationDTO> updateAssociation(@Valid @RequestBody AssociationDTO associationDTO) {
        log.debug("REST request to update Association : {}", associationDTO);
        if (associationDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        AssociationDTO result = associationService.update(associationDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/associations")
    public ResponseEntity<Page<AssociationDTO>> getAllAssociations(Pageable pageable) {
        log.debug("REST request to get a page of Associations");
        Page<AssociationDTO> page = associationService.findAll(pageable);
        return ResponseEntity.ok()
                .body(page);
    }

    @GetMapping("/associations/{id}")
    public ResponseEntity<AssociationDTO> getAssociation(@PathVariable Long id) {
        log.debug("REST request to get Association : {}", id);
        AssociationDTO associationDTO = associationService.findOne(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        return ResponseEntity.ok()
                .body(associationDTO);
    }

    @DeleteMapping("/associations/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long id) {
        log.debug("REST request to delete Association : {}", id);
        associationService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/association/checkAuthenticationStep2")
    public ResponseEntity<?> checkAuthenticationStep2(@RequestHeader("transactionID") String transactionID) {
        return associationService.checkAuthenticationStep2(transactionID);
    }

    @PostMapping("/association/pushCompteEntreprise")
    public ResponseEntity<?> pushCompteEntreprise(@RequestBody CompteEntrepriseDTO compteEntrepriseDTO) {
        try {
            associationService.pushCompteEntreprise(compteEntrepriseDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/processAuthenticationStep2")
    public ResponseEntity<?> processAuthenticationStep2(@RequestParam Long compteID, @RequestParam Long fs) {
        return associationService.processAuthenticationStep2(compteID, fs);
    }
}

