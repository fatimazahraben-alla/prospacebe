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
import java.time.ZoneOffset;

/**
 * Mapper for the entity {@link Procuration} and its DTO {@link ProcurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcurationMapper extends EntityMapper<ProcurationDTO, Procuration> {

    ProcurationMapper INSTANCE = Mappers.getMapper(ProcurationMapper.class);

    @Mapping(source = "gestionnaireEspacePro.id", target = "gestionnaireEspaceProId")
    @Mapping(source = "gestionnaireEspacePro.identifiant", target = "gestionnaireEspaceProIdentifiant")
    @Mapping(source = "utilisateurPro.id", target = "utilisateurProId")
    @Mapping(source = "utilisateurPro.identifiant", target = "utilisateurProIdentifiant")
    ProcurationDTO toDto(Procuration entity);

    @Mapping(source = "gestionnaireEspaceProId", target = "gestionnaireEspacePro.id")
    @Mapping(source = "utilisateurProId", target = "utilisateurPro.id")
    Procuration toEntity(ProcurationDTO dto);

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
            entity.setDateEffet((dto.getDateEffet()));
        }
        if (dto.getDateFin() != null) {
            entity.setDateFin((dto.getDateFin()));
        }
    }

    @Named("compteProId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteProDTO toDtoCompteProId(ComptePro comptePro);

    default Instant map(LocalDate date) {
        return date != null ? date.atStartOfDay().toInstant(ZoneOffset.UTC) : null;
    }

    default LocalDate map(Instant instant) {
        return instant != null ? instant.atZone(ZoneOffset.UTC).toLocalDate() : null;
    }
}



