package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FournisseurServiceMapper extends EntityMapper<FournisseurServiceDTO, FournisseurService> {

    FournisseurService toEntity(FournisseurServiceDTO dto);

    FournisseurServiceDTO toDto(FournisseurService entity);

    @Mapping(target = "roles", ignore = true) // Ignorer la conversion de roles dans ce sens
    void updateEntityFromDto(FournisseurServiceDTO dto, @MappingTarget FournisseurService entity);

    @Named("toDtoWithRoles")
    default FournisseurServiceDTO toDtoWithRoles(FournisseurService entity) {
        FournisseurServiceDTO dto = toDto(entity);
        dto.setRoles(entity.getRoles());
        return dto;
    }

    @Named("toEntityWithRoles")
    default FournisseurService toEntityWithRoles(FournisseurServiceDTO dto) {
        FournisseurService entity = toEntity(dto);
        entity.setRoles(dto.getRoles());
        return entity;
    }

    @Named("toDtoList")
    default List<FournisseurServiceDTO> toDtoList(List<FournisseurService> entityList) {
        return entityList.stream()
                .map(this::toDtoWithRoles)
                .collect(Collectors.toList());
    }

    @Named("toEntityList")
    default List<FournisseurService> toEntityList(List<FournisseurServiceDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntityWithRoles)
                .collect(Collectors.toList());
    }
}

