package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;

import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.EntrepriseDTO;

/**
 * Mapper for the entity {@link Entreprise} and its DTO {@link Entreprise}.
 */
@Mapper(componentModel = "spring")
public interface EntrepriseMapper extends EntityMapper<EntrepriseDTO, Entreprise> {}
