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

/**
 * Mapper for the entity {@link Association} and its DTO {@link Association}.
 */
@Mapper(componentModel = "spring")
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {
    @Mapping(target = "ENTREPRISEID", source = "entreprise", qualifiedByName = "entrepriseId")
    @Mapping(target = "COMPTEID", source = "compte", qualifiedByName = "compteProId")
    @Mapping(target = "ROLEID", source = "role", qualifiedByName = "roleeId")
    AssociationDTO toDto(Association s);

    @Named("entrepriseId")
    default Long mapEntrepriseToId(Entreprise entreprise) {
        return entreprise != null ? entreprise.getId() : null;
    }
    @Named("compteProId")
    default Long mapCompteToId(ComptePro compte) {
        return compte != null ? compte.getId() : null;
    }
    @Named("roleeId")
    default Long mapRoleToId(Rolee role) {
        return role != null ? role.getId() : null;
    }
    @Named("compteProId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteProDTO toDtoCompteProId(ComptePro comptePro);

    @Named("roleeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleeDTO toDtoRoleeId(Rolee rolee);
}
