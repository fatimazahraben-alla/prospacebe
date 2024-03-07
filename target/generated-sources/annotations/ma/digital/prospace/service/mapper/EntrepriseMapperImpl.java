package ma.digital.prospace.service.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T00:27:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class EntrepriseMapperImpl implements EntrepriseMapper {

    @Override
    public Entreprise toEntity(EntrepriseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setId( dto.getId() );
        entreprise.setDenomination( dto.getDenomination() );
        entreprise.setStatutJuridique( dto.getStatutJuridique() );
        entreprise.setTribunal( dto.getTribunal() );
        entreprise.setNumeroRC( dto.getNumeroRC() );
        entreprise.setIce( dto.getIce() );
        entreprise.setActivite( dto.getActivite() );
        entreprise.setFormeJuridique( dto.getFormeJuridique() );
        if ( dto.getDateImmatriculation() != null ) {
            entreprise.setDateImmatriculation( Instant.parse( dto.getDateImmatriculation() ) );
        }
        entreprise.setEtat( dto.getEtat() );

        return entreprise;
    }

    @Override
    public EntrepriseDTO toDto(Entreprise entity) {
        if ( entity == null ) {
            return null;
        }

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();

        entrepriseDTO.setId( entity.getId() );
        entrepriseDTO.setDenomination( entity.getDenomination() );
        entrepriseDTO.setStatutJuridique( entity.getStatutJuridique() );
        entrepriseDTO.setTribunal( entity.getTribunal() );
        entrepriseDTO.setNumeroRC( entity.getNumeroRC() );
        entrepriseDTO.setIce( entity.getIce() );
        entrepriseDTO.setActivite( entity.getActivite() );
        entrepriseDTO.setFormeJuridique( entity.getFormeJuridique() );
        if ( entity.getDateImmatriculation() != null ) {
            entrepriseDTO.setDateImmatriculation( entity.getDateImmatriculation().toString() );
        }
        entrepriseDTO.setEtat( entity.getEtat() );

        return entrepriseDTO;
    }

    @Override
    public List<Entreprise> toEntity(List<EntrepriseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Entreprise> list = new ArrayList<Entreprise>( dtoList.size() );
        for ( EntrepriseDTO entrepriseDTO : dtoList ) {
            list.add( toEntity( entrepriseDTO ) );
        }

        return list;
    }

    @Override
    public List<EntrepriseDTO> toDto(List<Entreprise> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EntrepriseDTO> list = new ArrayList<EntrepriseDTO>( entityList.size() );
        for ( Entreprise entreprise : entityList ) {
            list.add( toDto( entreprise ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Entreprise entity, EntrepriseDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDenomination() != null ) {
            entity.setDenomination( dto.getDenomination() );
        }
        if ( dto.getStatutJuridique() != null ) {
            entity.setStatutJuridique( dto.getStatutJuridique() );
        }
        if ( dto.getTribunal() != null ) {
            entity.setTribunal( dto.getTribunal() );
        }
        if ( dto.getNumeroRC() != null ) {
            entity.setNumeroRC( dto.getNumeroRC() );
        }
        if ( dto.getIce() != null ) {
            entity.setIce( dto.getIce() );
        }
        if ( dto.getActivite() != null ) {
            entity.setActivite( dto.getActivite() );
        }
        if ( dto.getFormeJuridique() != null ) {
            entity.setFormeJuridique( dto.getFormeJuridique() );
        }
        if ( dto.getDateImmatriculation() != null ) {
            entity.setDateImmatriculation( Instant.parse( dto.getDateImmatriculation() ) );
        }
        if ( dto.getEtat() != null ) {
            entity.setEtat( dto.getEtat() );
        }
    }
}
