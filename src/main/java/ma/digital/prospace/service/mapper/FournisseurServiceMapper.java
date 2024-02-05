package ma.digital.prospace.service.mapper;

import org.mapstruct.Mapper;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;

/**
 * Mapper for the entity {@link FournisseurService} and its DTO {@link FournisseurService}.
 */
@Mapper(componentModel = "spring")
public interface FournisseurServiceMapper extends EntityMapper<FournisseurServiceDTO, FournisseurService> {}
