package ma.digital.prospace.web.rest;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.CompteProService;
import ma.digital.prospace.service.InvitationService;
import ma.digital.prospace.service.dto.InvitationDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InvitationResource {
    private final Logger log = LoggerFactory.getLogger(InvitationService.class);
    private final InvitationService invitationService;

    @Autowired
    public InvitationResource(InvitationService invitationService) {
        this.invitationService = invitationService;
    }
    @GetMapping("/invitations")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        List<Invitation> invitations = invitationService.getAllInvitations();
        return ResponseEntity.ok().body(invitations);
    }
    @PostMapping("/invitations/{id}/accept")
    public ResponseEntity<InvitationDTO> acceptInvitation(@PathVariable Long id) {
        log.debug("REST request to accept Invitation : {}", id);
        InvitationDTO result = invitationService.acceptInvitation(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/invitations/{id}/cancel")
    public ResponseEntity<InvitationDTO> cancelInvitation(@PathVariable Long id) {
        log.debug("REST request to cancel Invitation : {}", id);
        InvitationDTO result = invitationService.cancelInvitation(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/invitations")
    public ResponseEntity<?> createInvitation(@RequestBody InvitationDTO invitationDTO) {
        try {
            InvitationDTO result = invitationService.createInvitation(invitationDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
