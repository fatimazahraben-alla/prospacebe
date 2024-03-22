package ma.digital.prospace.web.rest;

import ma.digital.prospace.service.InvitationService;
import ma.digital.prospace.service.dto.InvitationDTO;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }
}
