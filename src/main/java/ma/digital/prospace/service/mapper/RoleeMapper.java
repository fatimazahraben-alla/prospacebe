package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FournisseurServiceMapper.class})
public interface RoleeMapper {

    Rolee toEntity(RoleeDTO dto);

    RoleeDTO toDto(Rolee entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RoleeDTO dto, @MappingTarget Rolee entity);
    default void partialUpdate(@MappingTarget Rolee target, RoleeDTO source) {
        if (source.getFournisseurServiceId() != null) {
            target.setFs(map(source.getFournisseurServiceId()));
        }
        
    }

    default FournisseurService map(Long value) {
        if (value == null) {
            return null;
        }
        FournisseurService fournisseurService = new FournisseurService();
        fournisseurService.setId(value);
        return fournisseurService;
    }
}
