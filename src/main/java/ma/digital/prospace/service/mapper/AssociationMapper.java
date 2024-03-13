package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.service.dto.AssociationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Association} and its DTO {@link AssociationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssociationMapper extends EntityMapper<AssociationDTO, Association> {
    AssociationMapper INSTANCE = Mappers.getMapper(AssociationMapper.class);

    @Mapping(target = "compteID", source = "compte.id")
    @Mapping(target = "entrepriseID", source = "entreprise.id")
    @Mapping(target = "dateEffet", source = "dateEffet")
    @Mapping(target = "dateFin", source = "dateFin")
    @Mapping(target = "mail", source = "mail")
    @Mapping(target = "telephone", source = "telephone")
    AssociationDTO toDto(Association association);

    @Mapping(source = "compteID", target = "compte.id")
    @Mapping(source = "entrepriseID", target = "entreprise.id")
    @Mapping(source = "dateEffet", target = "dateEffet")
    @Mapping(source = "dateFin", target = "dateFin")
    @Mapping(source = "mail", target = "mail")
    @Mapping(source = "telephone", target = "telephone")
    Association toEntity(AssociationDTO associationDTO);
}
