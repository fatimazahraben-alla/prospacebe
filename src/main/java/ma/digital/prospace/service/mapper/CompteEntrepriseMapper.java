package ma.digital.prospace.service.mapper;

import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.mapstruct.Mapper;

import ma.digital.prospace.domain.CompteEntreprise;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO11;

/**
 * Mapper for the entity {@link CompteEntreprise} and its DTO {@link CompteEntrepriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteEntrepriseMapper extends EntityMapper<CompteEntrepriseDTO, CompteEntreprise> {


}


