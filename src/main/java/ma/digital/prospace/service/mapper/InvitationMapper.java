package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.dto.InvitationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompteProMapper.class})
public interface InvitationMapper {
    @Mapping(source = "comptePro.id", target = "compteProId")
    InvitationDTO toDto(Invitation invitation);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "compteProId", target = "comptePro.id")
    Invitation toEntity(InvitationDTO invitationDTO);
}
