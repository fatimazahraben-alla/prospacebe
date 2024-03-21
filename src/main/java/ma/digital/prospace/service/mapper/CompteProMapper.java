package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;

@Mapper(componentModel = "spring")
public interface CompteProMapper extends EntityMapper<CompteProDTO, ComptePro> {
    ComptePro toEntity(CompteProDTO compteProDTO);
    CompteProDTO toDto(ComptePro comptePro);

    default ComptePro fromId(Long id) {
        if (id == null) {
            return null;
        }
        ComptePro compte = new ComptePro();
        compte.setId(id);
        return compte;
    }
    @Named("entrepriseId")
    @Mapping(target = "id", source = "id")
    EntrepriseDTO toDtoEntrepriseId(Entreprise entreprise);
}

