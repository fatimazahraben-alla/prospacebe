package ma.digital.prospace.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import ma.digital.prospace.service.dto.RoleeDTO;

/**
 * Mapper for the entity {@link Rolee} and its DTO {@link Rolee}.
 */
@Mapper(componentModel = "spring")
public interface RoleeMapper extends EntityMapper<RoleeDTO, Rolee> {
    @Mapping(target = "fs", source = "fs", qualifiedByName = "fournisseurServiceId")
    RoleeDTO toDto(Rolee s);

    @Named("fournisseurServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FournisseurServiceDTO toDtoFournisseurServiceId(FournisseurService fournisseurService);
}
