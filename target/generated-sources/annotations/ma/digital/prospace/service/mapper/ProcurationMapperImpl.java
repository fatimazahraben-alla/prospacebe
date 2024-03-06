package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import ma.digital.prospace.service.dto.ProcurationDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-06T11:25:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ProcurationMapperImpl implements ProcurationMapper {

    @Override
    public Procuration toEntity(ProcurationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Procuration procuration = new Procuration();

        procuration.setId( dto.getId() );
        procuration.setDateEffet( dto.getDateEffet() );
        procuration.setDateFin( dto.getDateFin() );
        procuration.gestionnaireEspacePro( compteProDTOToComptePro( dto.getGestionnaireEspacePro() ) );
        procuration.utilisateurPro( compteProDTOToComptePro( dto.getUtilisateurPro() ) );

        return procuration;
    }

    @Override
    public List<Procuration> toEntity(List<ProcurationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Procuration> list = new ArrayList<Procuration>( dtoList.size() );
        for ( ProcurationDTO procurationDTO : dtoList ) {
            list.add( toEntity( procurationDTO ) );
        }

        return list;
    }

    @Override
    public List<ProcurationDTO> toDto(List<Procuration> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProcurationDTO> list = new ArrayList<ProcurationDTO>( entityList.size() );
        for ( Procuration procuration : entityList ) {
            list.add( toDto( procuration ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Procuration entity, ProcurationDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDateEffet() != null ) {
            entity.setDateEffet( dto.getDateEffet() );
        }
        if ( dto.getDateFin() != null ) {
            entity.setDateFin( dto.getDateFin() );
        }
        if ( dto.getGestionnaireEspacePro() != null ) {
            if ( entity.getGestionnaireEspacePro() == null ) {
                entity.gestionnaireEspacePro( new ComptePro() );
            }
            compteProDTOToComptePro1( dto.getGestionnaireEspacePro(), entity.getGestionnaireEspacePro() );
        }
        if ( dto.getUtilisateurPro() != null ) {
            if ( entity.getUtilisateurPro() == null ) {
                entity.utilisateurPro( new ComptePro() );
            }
            compteProDTOToComptePro1( dto.getUtilisateurPro(), entity.getUtilisateurPro() );
        }
    }

    @Override
    public ProcurationDTO toDto(Procuration s) {
        if ( s == null ) {
            return null;
        }

        ProcurationDTO procurationDTO = new ProcurationDTO();

        procurationDTO.setGestionnaireEspacePro( toDtoCompteProId( s.getGestionnaireEspacePro() ) );
        procurationDTO.setUtilisateurPro( toDtoCompteProId( s.getUtilisateurPro() ) );
        procurationDTO.setId( s.getId() );
        procurationDTO.setDateEffet( s.getDateEffet() );
        procurationDTO.setDateFin( s.getDateFin() );

        return procurationDTO;
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

    protected ComptePro compteProDTOToComptePro(CompteProDTO compteProDTO) {
        if ( compteProDTO == null ) {
            return null;
        }

        ComptePro comptePro = new ComptePro();

        comptePro.setId( compteProDTO.getId() );
        comptePro.setIdentifiant( compteProDTO.getIdentifiant() );
        comptePro.setTypeIdentifiant( compteProDTO.getTypeIdentifiant() );
        comptePro.setNomAr( compteProDTO.getNomAr() );
        comptePro.setNomFr( compteProDTO.getNomFr() );
        comptePro.setPrenomAr( compteProDTO.getPrenomAr() );
        comptePro.setPrenomFr( compteProDTO.getPrenomFr() );
        comptePro.setAdresse( compteProDTO.getAdresse() );
        byte[] photo = compteProDTO.getPhoto();
        if ( photo != null ) {
            comptePro.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        comptePro.setPhotoContentType( compteProDTO.getPhotoContentType() );
        comptePro.setMail( compteProDTO.getMail() );
        comptePro.setTelephone( compteProDTO.getTelephone() );
        comptePro.setStatut( compteProDTO.getStatut() );
        comptePro.entrepriseGeree( entrepriseDTOToEntreprise( compteProDTO.getEntrepriseGeree() ) );

        return comptePro;
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

    protected void compteProDTOToComptePro1(CompteProDTO compteProDTO, ComptePro mappingTarget) {
        if ( compteProDTO == null ) {
            return;
        }

        if ( compteProDTO.getId() != null ) {
            mappingTarget.setId( compteProDTO.getId() );
        }
        if ( compteProDTO.getIdentifiant() != null ) {
            mappingTarget.setIdentifiant( compteProDTO.getIdentifiant() );
        }
        if ( compteProDTO.getTypeIdentifiant() != null ) {
            mappingTarget.setTypeIdentifiant( compteProDTO.getTypeIdentifiant() );
        }
        if ( compteProDTO.getNomAr() != null ) {
            mappingTarget.setNomAr( compteProDTO.getNomAr() );
        }
        if ( compteProDTO.getNomFr() != null ) {
            mappingTarget.setNomFr( compteProDTO.getNomFr() );
        }
        if ( compteProDTO.getPrenomAr() != null ) {
            mappingTarget.setPrenomAr( compteProDTO.getPrenomAr() );
        }
        if ( compteProDTO.getPrenomFr() != null ) {
            mappingTarget.setPrenomFr( compteProDTO.getPrenomFr() );
        }
        if ( compteProDTO.getAdresse() != null ) {
            mappingTarget.setAdresse( compteProDTO.getAdresse() );
        }
        byte[] photo = compteProDTO.getPhoto();
        if ( photo != null ) {
            mappingTarget.setPhoto( Arrays.copyOf( photo, photo.length ) );
        }
        if ( compteProDTO.getPhotoContentType() != null ) {
            mappingTarget.setPhotoContentType( compteProDTO.getPhotoContentType() );
        }
        if ( compteProDTO.getMail() != null ) {
            mappingTarget.setMail( compteProDTO.getMail() );
        }
        if ( compteProDTO.getTelephone() != null ) {
            mappingTarget.setTelephone( compteProDTO.getTelephone() );
        }
        if ( compteProDTO.getStatut() != null ) {
            mappingTarget.setStatut( compteProDTO.getStatut() );
        }
        if ( compteProDTO.getEntrepriseGeree() != null ) {
            if ( mappingTarget.getEntrepriseGeree() == null ) {
                mappingTarget.entrepriseGeree( new Entreprise() );
            }
            entrepriseDTOToEntreprise1( compteProDTO.getEntrepriseGeree(), mappingTarget.getEntrepriseGeree() );
        }
    }
}
