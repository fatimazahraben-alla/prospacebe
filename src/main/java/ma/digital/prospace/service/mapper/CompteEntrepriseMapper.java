package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;

import ma.digital.prospace.domain.CompteEntreprise;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.EntrepriseRequest;

/**
 * Mapper for the entity {@link CompteEntreprise} and its DTO {@link CompteEntrepriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteEntrepriseMapper extends EntityMapper<CompteEntrepriseDTO, CompteEntreprise> {


    default EntrepriseRequest entrepriseToEntrepriseDTO(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }
        EntrepriseRequest entrepriseRequest = new EntrepriseRequest();
        entrepriseRequest.setId(entreprise.getId());
        // Map other properties if needed
        return entrepriseRequest;
    }
}


