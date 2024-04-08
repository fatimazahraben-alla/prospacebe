package ma.digital.prospace.web.rest;

import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.InvitationService;
import ma.digital.prospace.service.dto.InvitationDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InvitationResource {

    private final InvitationService invitationService;

    @Autowired
    public InvitationResource(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/invitations")
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationDTO invitationDTO) {
        InvitationDTO result = invitationService.createInvitation(invitationDTO);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/invitations")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        List<Invitation> invitations = invitationService.getAllInvitations();
        return ResponseEntity.ok().body(invitations);
    }
}
