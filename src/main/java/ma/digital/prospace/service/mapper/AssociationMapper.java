package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.service.dto.AssociationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

/**
 * Mapper for the entity {@link Association} and its DTO {@link AssociationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {
    AssociationMapper INSTANCE = Mappers.getMapper(AssociationMapper.class);

    @Mapping(target = "compteID", source = "compte.id")
    @Mapping(target = "entrepriseID", source = "entreprise.id")
    @Mapping(target = "roleID", source = "role.id")
    @Mapping(target = "dateEffet", source = "dateEffet")
    @Mapping(target = "dateFin", source = "dateFin")
    @Mapping(target = "mail", source = "mail")
    @Mapping(target = "telephone", source = "telephone")
    @Mapping(target = "compteInitiateurID", source = "compteInitiateurID") // Corrigé
    AssociationDTO toDto(Association association);

    @Mapping(source = "compteID", target = "compte.id")
    @Mapping(source = "entrepriseID", target = "entreprise.id")
    @Mapping(source = "roleID", target = "role.id")
    @Mapping(source = "dateEffet", target = "dateEffet")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "mail", target = "mail")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "compteInitiateurID", target = "compteInitiateurID") // Corrigé
    Association toEntity(AssociationDTO associationDTO);

    default Association fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Association association = new Association();
        association.setId(id);
        return association;
    }
}