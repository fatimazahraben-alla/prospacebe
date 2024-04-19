package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RoleeMapper {

    @Mapping(target = "fournisseurServiceId", source = "fs.id")
    RoleeDTO toDto(Rolee rolee);

    @Mapping(target = "fs", source = "fournisseurServiceId")
    Rolee toEntity(RoleeDTO roleeDTO);

    default void partialUpdate(@MappingTarget Rolee target, RoleeDTO source) {
        if (source.getFournisseurServiceId() != null) {
            target.setFs(map(source.getFournisseurServiceId()));
        }
        
    }

    default FournisseurService map(String value) {
        if (value == null) {
            return null;
        }
        FournisseurService fournisseurService = new FournisseurService();
        fournisseurService.setId(value);
        return fournisseurService;
    }
}
