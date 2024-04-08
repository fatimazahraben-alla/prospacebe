package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.service.dto.InvitationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompteProMapper.class})
public interface InvitationMapper {

}
