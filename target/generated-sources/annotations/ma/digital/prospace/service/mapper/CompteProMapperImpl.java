package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T18:00:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CompteProMapperImpl implements CompteProMapper {

    @Override
    public ComptePro toEntity(CompteProDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ComptePro comptePro = new ComptePro();

        comptePro.setId( dto.getId() );
        comptePro.setIdentifiant( dto.getIdentifiant() );
        comptePro.setTypeIdentifiant( dto.getTypeIdentifiant() );
        comptePro.setNomAr( dto.getNomAr() );
        comptePro.setNomFr( dto.getNomFr() );
        comptePro.setPrenomAr( dto.getPrenomAr() );
        comptePro.setPrenomFr( dto.getPrenomFr() );
        byte[] photo = dto.getPhoto();
        if ( photo != null ) {
            comptePro.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        comptePro.setMail( dto.getMail() );
        comptePro.setTelephone( dto.getTelephone() );
        comptePro.setStatut( dto.getStatut() );

        return comptePro;
    }

    @Override
    public CompteProDTO toDto(ComptePro entity) {
        if ( entity == null ) {
            return null;
        }

        CompteProDTO compteProDTO = new CompteProDTO();

        compteProDTO.setId( entity.getId() );
        compteProDTO.setIdentifiant( entity.getIdentifiant() );
        compteProDTO.setTypeIdentifiant( entity.getTypeIdentifiant() );
        compteProDTO.setNomAr( entity.getNomAr() );
        compteProDTO.setNomFr( entity.getNomFr() );
        compteProDTO.setPrenomAr( entity.getPrenomAr() );
        compteProDTO.setPrenomFr( entity.getPrenomFr() );
        byte[] photo = entity.getPhoto();
        if ( photo != null ) {
            compteProDTO.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        compteProDTO.setMail( entity.getMail() );
        compteProDTO.setTelephone( entity.getTelephone() );
        compteProDTO.setStatut( entity.getStatut() );

        return compteProDTO;
    }

    @Override
    public List<ComptePro> toEntity(List<CompteProDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ComptePro> list = new ArrayList<ComptePro>( dtoList.size() );
        for ( CompteProDTO compteProDTO : dtoList ) {
            list.add( toEntity( compteProDTO ) );
        }

        return list;
    }

    @Override
    public List<CompteProDTO> toDto(List<ComptePro> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CompteProDTO> list = new ArrayList<CompteProDTO>( entityList.size() );
        for ( ComptePro comptePro : entityList ) {
            list.add( toDto( comptePro ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ComptePro entity, CompteProDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getIdentifiant() != null ) {
            entity.setIdentifiant( dto.getIdentifiant() );
        }
        if ( dto.getTypeIdentifiant() != null ) {
            entity.setTypeIdentifiant( dto.getTypeIdentifiant() );
        }
        if ( dto.getNomAr() != null ) {
            entity.setNomAr( dto.getNomAr() );
        }
        if ( dto.getNomFr() != null ) {
            entity.setNomFr( dto.getNomFr() );
        }
        if ( dto.getPrenomAr() != null ) {
            entity.setPrenomAr( dto.getPrenomAr() );
        }
        if ( dto.getPrenomFr() != null ) {
            entity.setPrenomFr( dto.getPrenomFr() );
        }
        byte[] photo = dto.getPhoto();
        if ( photo != null ) {
            entity.setPhoto( Arrays.copyOf( photo, photo.length ) );
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
    public EntrepriseDTO toDtoEntrepriseId(Entreprise entreprise) {
        if ( entreprise == null ) {
            return null;
        }

        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();

        entrepriseDTO.setId( entreprise.getId() );

        return entrepriseDTO;
    }
}
