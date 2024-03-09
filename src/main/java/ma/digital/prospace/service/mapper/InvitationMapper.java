package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.dto.InvitationDTO;
import org.springframework.stereotype.Component;

@Component
public interface InvitationMapper {

    InvitationDTO invitationToInvitationDTO(Invitation invitation);

    Invitation invitationDTOToInvitation(InvitationDTO invitationDTO);

    // You can add @Mapping annotations if field names do not match
}

