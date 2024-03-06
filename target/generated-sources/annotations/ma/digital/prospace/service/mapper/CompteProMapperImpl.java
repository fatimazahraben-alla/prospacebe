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
    date = "2024-03-06T11:25:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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
        comptePro.setAdresse( dto.getAdresse() );
        byte[] photo = dto.getPhoto();
        if ( photo != null ) {
            comptePro.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        comptePro.setPhotoContentType( dto.getPhotoContentType() );
        comptePro.setMail( dto.getMail() );
        comptePro.setTelephone( dto.getTelephone() );
        comptePro.setStatut( dto.getStatut() );
        comptePro.entrepriseGeree( entrepriseDTOToEntreprise( dto.getEntrepriseGeree() ) );

        return comptePro;
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
        if ( dto.getAdresse() != null ) {
            entity.setAdresse( dto.getAdresse() );
        }
        byte[] photo = dto.getPhoto();
        if ( photo != null ) {
            entity.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        if ( dto.getPhotoContentType() != null ) {
            entity.setPhotoContentType( dto.getPhotoContentType() );
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
        if ( dto.getEntrepriseGeree() != null ) {
            if ( entity.getEntrepriseGeree() == null ) {
                entity.entrepriseGeree( new Entreprise() );
            }
            entrepriseDTOToEntreprise1( dto.getEntrepriseGeree(), entity.getEntrepriseGeree() );
        }
    }

    @Override
    public CompteProDTO toDto(ComptePro s) {
        if ( s == null ) {
            return null;
        }

        CompteProDTO compteProDTO = new CompteProDTO();

        compteProDTO.setEntrepriseGeree( toDtoEntrepriseId( s.getEntrepriseGeree() ) );
        compteProDTO.setId( s.getId() );
        compteProDTO.setIdentifiant( s.getIdentifiant() );
        compteProDTO.setTypeIdentifiant( s.getTypeIdentifiant() );
        compteProDTO.setNomAr( s.getNomAr() );
        compteProDTO.setNomFr( s.getNomFr() );
        compteProDTO.setPrenomAr( s.getPrenomAr() );
        compteProDTO.setPrenomFr( s.getPrenomFr() );
        compteProDTO.setAdresse( s.getAdresse() );
        byte[] photo = s.getPhoto();
        if ( photo != null ) {
            compteProDTO.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        compteProDTO.setPhotoContentType( s.getPhotoContentType() );
        compteProDTO.setMail( s.getMail() );
        compteProDTO.setTelephone( s.getTelephone() );
        compteProDTO.setStatut( s.getStatut() );

        return compteProDTO;
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

    protected Entreprise entrepriseDTOToEntreprise(EntrepriseDTO entrepriseDTO) {
        if ( entrepriseDTO == null ) {
            return null;
        }

        Entreprise entreprise = new Entreprise();

        entreprise.setId( entrepriseDTO.getId() );
        entreprise.setDenomination( entrepriseDTO.getDenomination() );
        entreprise.setStatutJuridique( entrepriseDTO.getStatutJuridique() );
        entreprise.setTribunal( entrepriseDTO.getTribunal() );
        entreprise.setNumeroRC( entrepriseDTO.getNumeroRC() );
        entreprise.setIce( entrepriseDTO.getIce() );
        entreprise.setActivite( entrepriseDTO.getActivite() );
        entreprise.setFormeJuridique( entrepriseDTO.getFormeJuridique() );
        entreprise.setDateImmatriculation( entrepriseDTO.getDateImmatriculation() );
        entreprise.setEtat( entrepriseDTO.getEtat() );

        return entreprise;
    }

    protected void entrepriseDTOToEntreprise1(EntrepriseDTO entrepriseDTO, Entreprise mappingTarget) {
        if ( entrepriseDTO == null ) {
            return;
        }

        if ( entrepriseDTO.getId() != null ) {
            mappingTarget.setId( entrepriseDTO.getId() );
        }
        if ( entrepriseDTO.getDenomination() != null ) {
            mappingTarget.setDenomination( entrepriseDTO.getDenomination() );
        }
        if ( entrepriseDTO.getStatutJuridique() != null ) {
            mappingTarget.setStatutJuridique( entrepriseDTO.getStatutJuridique() );
        }
        if ( entrepriseDTO.getTribunal() != null ) {
            mappingTarget.setTribunal( entrepriseDTO.getTribunal() );
        }
        if ( entrepriseDTO.getNumeroRC() != null ) {
            mappingTarget.setNumeroRC( entrepriseDTO.getNumeroRC() );
        }
        if ( entrepriseDTO.getIce() != null ) {
            mappingTarget.setIce( entrepriseDTO.getIce() );
        }
        if ( entrepriseDTO.getActivite() != null ) {
            mappingTarget.setActivite( entrepriseDTO.getActivite() );
        }
        if ( entrepriseDTO.getFormeJuridique() != null ) {
            mappingTarget.setFormeJuridique( entrepriseDTO.getFormeJuridique() );
        }
        if ( entrepriseDTO.getDateImmatriculation() != null ) {
            mappingTarget.setDateImmatriculation( entrepriseDTO.getDateImmatriculation() );
        }
        if ( entrepriseDTO.getEtat() != null ) {
            mappingTarget.setEtat( entrepriseDTO.getEtat() );
        }
    }
}
