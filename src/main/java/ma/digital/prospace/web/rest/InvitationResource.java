package ma.digital.prospace.web.rest;


import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.InvitationService;
import ma.digital.prospace.service.dto.InvitationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
public class InvitationResource {

    private final InvitationService invitationService;

    @Autowired
    public InvitationResource(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/invitations")
    @PreAuthorize("hasAnyAuthority('ROLE_GESTIONNAIREESPACE', 'ROLE_GESTIONNAIREENTREPRISE')")
    public ResponseEntity<InvitationDTO> createInvitation(@RequestBody InvitationDTO invitationDTO) {
        InvitationDTO result = invitationService.createInvitation(invitationDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/invitations")
    @PreAuthorize("hasAnyAuthority('ROLE_GESTIONNAIREESPACE', 'ROLE_GESTIONNAIREENTREPRISE')")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        List<Invitation> invitations = invitationService.getAllInvitations();
        return ResponseEntity.ok().body(invitations);
    }
}
