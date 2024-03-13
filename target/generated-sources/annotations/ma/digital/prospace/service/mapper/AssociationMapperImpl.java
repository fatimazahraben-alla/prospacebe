package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.AssociationDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-13T14:51:47+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class AssociationMapperImpl implements AssociationMapper {

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
        if ( dto.getDateEffet() != null ) {
            entity.setDateEffet( Date.from( dto.getDateEffet() ) );
        }
        if ( dto.getDateFin() != null ) {
            entity.setDateFin( Date.from( dto.getDateFin() ) );
        }
        if ( dto.getMail() != null ) {
            entity.setMail( dto.getMail() );
        }
        if ( dto.getTelephone() != null ) {
            entity.setTelephone( dto.getTelephone() );
        }
        if ( dto.getStatut() != null ) {
            entity.setStatut( dto.getStatut() );
        }
    }

    @Override
    public AssociationDTO toDto(Association association) {
        if ( association == null ) {
            return null;
        }

        AssociationDTO associationDTO = new AssociationDTO();

        associationDTO.setCompteID( associationCompteId( association ) );
        associationDTO.setEntrepriseID( associationEntrepriseId( association ) );
        if ( association.getDateEffet() != null ) {
            associationDTO.setDateEffet( association.getDateEffet().toInstant() );
        }
        if ( association.getDateFin() != null ) {
            associationDTO.setDateFin( association.getDateFin().toInstant() );
        }
        associationDTO.setMail( association.getMail() );
        associationDTO.setTelephone( association.getTelephone() );
        associationDTO.setId( association.getId() );
        associationDTO.setStatut( association.getStatut() );

        return associationDTO;
    }

    @Override
    public Association toEntity(AssociationDTO associationDTO) {
        if ( associationDTO == null ) {
            return null;
        }

        Association association = new Association();

        association.setCompte( associationDTOToComptePro( associationDTO ) );
        association.setEntreprise( associationDTOToEntreprise( associationDTO ) );
        if ( associationDTO.getDateEffet() != null ) {
            association.setDateEffet( Date.from( associationDTO.getDateEffet() ) );
        }
        if ( associationDTO.getDateFin() != null ) {
            association.setDateFin( Date.from( associationDTO.getDateFin() ) );
        }
        association.setMail( associationDTO.getMail() );
        association.setTelephone( associationDTO.getTelephone() );
        association.setId( associationDTO.getId() );
        association.setStatut( associationDTO.getStatut() );

        return association;
    }

    private Long associationCompteId(Association association) {
        if ( association == null ) {
            return null;
        }
        ComptePro compte = association.getCompte();
        if ( compte == null ) {
            return null;
        }
        Long id = compte.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long associationEntrepriseId(Association association) {
        if ( association == null ) {
            return null;
        }
        Entreprise entreprise = association.getEntreprise();
        if ( entreprise == null ) {
            return null;
        }
        Long id = entreprise.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ComptePro associationDTOToComptePro(AssociationDTO associationDTO) {
        if ( associationDTO == null ) {
            return null;
        }

        ComptePro comptePro = new ComptePro();

        comptePro.setId( associationDTO.getCompteID() );

        return comptePro;
    }

    protected Entreprise associationDTOToEntreprise(AssociationDTO associationDTO) {
        if ( associationDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setId( associationDTO.getEntrepriseID() );

        return entreprise;
    }
}
