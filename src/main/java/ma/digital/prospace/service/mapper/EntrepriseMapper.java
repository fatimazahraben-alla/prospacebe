package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.EntrepriseRequest;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Entreprise} and its DTO {@link EntrepriseRequest}.
 */
@Mapper(componentModel = "spring")
public interface EntrepriseMapper extends EntityMapper<EntrepriseRequest, Entreprise> {

    EntrepriseMapper INSTANCE = Mappers.getMapper(EntrepriseMapper.class);

}
