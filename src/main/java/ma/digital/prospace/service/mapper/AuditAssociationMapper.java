package ma.digital.prospace.service.mapper;

import ma.digital.prospace.domain.AuditAssociation;
import ma.digital.prospace.service.dto.AuditAssociationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuditAssociationMapper extends EntityMapper<AuditAssociationDTO, AuditAssociation> {

    AuditAssociationMapper INSTANCE = Mappers.getMapper(AuditAssociationMapper.class);

    default AuditAssociationDTO toDto(AuditAssociation auditAssociation) {
        return new AuditAssociationDTO(auditAssociation.getId(),
                auditAssociation.getAction(),
                auditAssociation.getAssociationId(),
                auditAssociation.getCompteId(),
                auditAssociation.getTimestamp());
    }

    default AuditAssociation toEntity(AuditAssociationDTO auditAssociationDTO) {
        AuditAssociation auditAssociation = new AuditAssociation();
        auditAssociation.setId(auditAssociationDTO.getId());
        auditAssociation.setAction(auditAssociationDTO.getAction());
        auditAssociation.setAssociationId(auditAssociationDTO.getAssociationId());
        auditAssociation.setCompteId(auditAssociationDTO.getCompteId());
        auditAssociation.setTimestamp(auditAssociationDTO.getTimestamp());
        return auditAssociation;
    }
}
