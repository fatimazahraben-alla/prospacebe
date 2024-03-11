package ma.digital.prospace.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;

/**
 * Mapper for the entity {@link ComptePro} and its DTO {@link ComptePro}.
 */
@Mapper(componentModel = "spring")
public interface CompteProMapper extends EntityMapper<CompteProDTO, ComptePro> {


}
