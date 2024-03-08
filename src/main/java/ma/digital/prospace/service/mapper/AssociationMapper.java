package ma.digital.prospace.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import ma.digital.prospace.service.dto.RoleeDTO;

@Mapper(componentModel = "spring")
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {
    @Mapping(target = "entrepriseID", source = "entreprise", qualifiedByName = "entrepriseId")
    @Mapping(target = "compteID", source = "compte", qualifiedByName = "compteProId")
    @Mapping(target = "roleID", source = "role", qualifiedByName = "roleeId")
    AssociationDTO toDto(Association s);


    @Named("entrepriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntrepriseDTO toDtoEntrepriseId(Entreprise entreprise);

    @Named("compteProId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteProDTO toDtoCompteProId(ComptePro comptePro);

    @Named("roleeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleeDTO toDtoRoleeId(Rolee rolee);

    default Long map(Entreprise entreprise) {
        return entreprise != null ? entreprise.getId() : null;
    }

    default Long map(ComptePro comptePro) {
        return comptePro != null ? comptePro.getId() : null;
    }

    default Long map(Rolee rolee) {
        return rolee != null ? rolee.getId() : null;
    }
}

