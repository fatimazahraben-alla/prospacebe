package ma.digital.prospace.service.mapper;

import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.mapstruct.Mapper;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.EntrepriseDTO11;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Entreprise} and its DTO {@link EntrepriseDTO11}.
 */
@Mapper(componentModel = "spring")
public interface EntrepriseMapper extends EntityMapper<EntrepriseDTO, Entreprise> {

    EntrepriseMapper INSTANCE = Mappers.getMapper(EntrepriseMapper.class);

}
