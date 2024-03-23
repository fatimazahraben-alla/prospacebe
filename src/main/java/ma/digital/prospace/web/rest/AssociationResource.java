package ma.digital.prospace.web.rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.AssociationService;
import ma.digital.prospace.service.FCMService;
import ma.digital.prospace.service.dto.*;
import ma.digital.prospace.service.mapper.AssociationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

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

    private FCMService fcmService;

    public AssociationResource(AssociationService associationService, FCMService fcmService) {
        this.associationService = associationService;
        this.fcmService =  fcmService;
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

    @GetMapping("/association/processAuthenticationStep2")
    public ResponseEntity<CompteFSAssociationDTO> processAuthenticationStep2(@RequestParam Long compteID, @RequestParam Long fs,
                                                                             @RequestParam String transactionID) {
        CompteFSAssociationDTO responseDTO = associationService.processAuthenticationStep2(compteID, fs, transactionID);
        if (responseDTO != null) {
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/association/pushCompteEntreprise")
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
    public ResponseEntity<CompteEntrepriseDTO> checkAuthenticationStep2(@RequestParam String transactionId) {
        try {
            CompteEntrepriseDTO compteEntrepriseDTO = associationService.checkAuthenticationStep2(transactionId);
            return ResponseEntity.ok(compteEntrepriseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //////just pour le testing///////
   /* @PostMapping("/notification")
    public ResponseEntity<String> sendNotification(
            @RequestParam String deviceToken,
            @RequestParam List<String> entrepriseList,
            @RequestParam String transactionID,
            @RequestParam Long fs,
            @RequestParam Long compteID,
            @RequestParam String Title,
            @RequestParam String Body
    ) {
        String result = associationService.constructAndSendPushNotification(deviceToken, entrepriseList, transactionID, fs, compteID, Title, Body);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
*/
    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
 /*   @PostMapping("/notification1")
    public ResponseEntity<String> sendNotification(
            @RequestParam String deviceToken,
            @RequestParam List<String> entrepriseList,
            @RequestParam String transactionID,
            @RequestParam Long fs,
            @RequestParam Long compteID,
            @RequestParam String Title,
            @RequestParam String Body
    ) {
        String result = associationService.constructAndSendPushNotification(deviceToken, entrepriseList, transactionID, fs, compteID, Title, Body);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }*/
}

