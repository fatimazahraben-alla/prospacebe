package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.dto.InvitationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompteProMapper.class})
public interface InvitationMapper {
    @Mapping(source = "comptePro.id", target = "compteProId")
    @Mapping(source = "compteProDestinataire.id", target = "compteProDestinataireId")
    InvitationDTO toDto(Invitation invitation);

    @Mapping(source = "compteProId", target = "comptePro.id")
    @Mapping(source = "compteProDestinataireId", target = "compteProDestinataire.id")
    Invitation toEntity(InvitationDTO invitationDTO);
}
