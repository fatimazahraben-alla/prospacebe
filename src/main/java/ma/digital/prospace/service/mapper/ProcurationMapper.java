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
    @Mapping(target = "gestionnaireEspacePro", source = "gestionnaireEspacePro", qualifiedByName = "compteProId")
    @Mapping(target = "utilisateurPro", source = "utilisateurPro", qualifiedByName = "compteProId")
    ProcurationDTO toDto(Procuration s);

    @Named("compteProId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteProDTO toDtoCompteProId(ComptePro comptePro);


}
