package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ma.digital.prospace.domain.CompteEntreprise;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;

/**
 * Mapper for the entity {@link CompteEntreprise} and its DTO {@link CompteEntrepriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteEntrepriseMapper extends EntityMapper<CompteEntrepriseDTO, CompteEntreprise> {


    default EntrepriseDTO entrepriseToEntrepriseDTO(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(entreprise.getId());
        // Map other properties if needed
        return entrepriseDTO;
    }
}


