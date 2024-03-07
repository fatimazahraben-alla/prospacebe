package ma.digital.prospace.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.ProcurationDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Mapper for the entity {@link Procuration} and its DTO {@link Procuration}.
 */
@Mapper(componentModel = "spring")
public interface ProcurationMapper extends EntityMapper<ProcurationDTO, Procuration> {

    ProcurationMapper INSTANCE = Mappers.getMapper(ProcurationMapper.class);

    @Mapping(target = "dateEffet", qualifiedByName = "localDateToInstant")
    @Mapping(target = "dateFin", qualifiedByName = "localDateToInstant")
    Procuration toEntity(ProcurationDTO dto);

    @Mapping(target = "dateEffet", qualifiedByName = "instantToLocalDate")
    @Mapping(target = "dateFin", qualifiedByName = "instantToLocalDate")
    ProcurationDTO toDto(Procuration entity);

    @Named("localDateToInstant")
    default Instant mapLocalDateToInstant(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay().toInstant(ZoneOffset.UTC) : null;
    }

    @Named("instantToLocalDate")
    default LocalDate mapInstantToLocalDate(Instant instant) {
        return instant != null ? instant.atZone(ZoneOffset.UTC).toLocalDate() : null;
    }

    default void partialUpdate(Procuration entity, ProcurationDTO dto) {
        if (dto.getDateEffet() != null) {
            entity.setDateEffet(mapLocalDateToInstant(dto.getDateEffet()));
        }
        // Autres mises à jour partielles si nécessaire
    }
    @Named("compteProId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteProDTO toDtoCompteProId(ComptePro comptePro);
}
