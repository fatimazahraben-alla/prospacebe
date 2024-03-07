package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T18:00:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AssociationMapperImpl implements AssociationMapper {

    @Override
    public Association toEntity(AssociationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Association association = new Association();

        association.setId( dto.getId() );
        association.setStatut( dto.getStatut() );

        return association;
    }

    @Override
    public List<Association> toEntity(List<AssociationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Association> list = new ArrayList<Association>( dtoList.size() );
        for ( AssociationDTO associationDTO : dtoList ) {
            list.add( toEntity( associationDTO ) );
        }

        return list;
    }

    @Override
    public List<AssociationDTO> toDto(List<Association> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AssociationDTO> list = new ArrayList<AssociationDTO>( entityList.size() );
        for ( Association association : entityList ) {
            list.add( toDto( association ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Association entity, AssociationDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getStatut() != null ) {
            entity.setStatut( dto.getStatut() );
        }
    }

    @Override
    public AssociationDTO toDto(Association s) {
        if ( s == null ) {
            return null;
        }

        AssociationDTO associationDTO = new AssociationDTO();

        associationDTO.setEntrepriseID( mapEntrepriseToId( s.getEntreprise() ) );
        associationDTO.setCompteID( mapCompteToId( s.getCompte() ) );
        associationDTO.setRoleID( mapRoleToId( s.getRole() ) );
        associationDTO.setId( s.getId() );
        associationDTO.setStatut( s.getStatut() );

        return associationDTO;
    }

    @Override
    public CompteProDTO toDtoCompteProId(ComptePro comptePro) {
        if ( comptePro == null ) {
            return null;
        }

        CompteProDTO compteProDTO = new CompteProDTO();

        compteProDTO.setId( comptePro.getId() );

        return compteProDTO;
    }

    @Override
    public RoleeDTO toDtoRoleeId(Rolee rolee) {
        if ( rolee == null ) {
            return null;
        }

        RoleeDTO roleeDTO = new RoleeDTO();

        roleeDTO.setId( rolee.getId() );

        return roleeDTO;
    }
}
