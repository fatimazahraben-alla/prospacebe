package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-06T11:25:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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
        if ( dto.getCompte() != null ) {
            if ( entity.getCompte() == null ) {
                entity.compte( new ComptePro() );
            }
            compteProDTOToComptePro1( dto.getCompte(), entity.getCompte() );
        }
        if ( dto.getRole() != null ) {
            if ( entity.getRole() == null ) {
                entity.role( new Rolee() );
            }
            roleeDTOToRolee1( dto.getRole(), entity.getRole() );
        }
    }

    @Override
    public AssociationDTO toDto(Association s) {
        if ( s == null ) {
            return null;
        }

        AssociationDTO associationDTO = new AssociationDTO();

        associationDTO.setEntreprise( toDtoEntrepriseId( s.getEntreprise() ) );
        associationDTO.setCompte( toDtoCompteProId( s.getCompte() ) );
        associationDTO.setRole( toDtoRoleeId( s.getRole() ) );
        associationDTO.setId( s.getId() );
        associationDTO.setDateEffet( s.getDateEffet() );
        associationDTO.setDateFin( s.getDateFin() );
        associationDTO.setMail( s.getMail() );
        associationDTO.setTelephone( s.getTelephone() );
        associationDTO.setStatut( s.getStatut() );

        return associationDTO;
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

    protected FournisseurService fournisseurServiceDTOToFournisseurService(FournisseurServiceDTO fournisseurServiceDTO) {
        if ( fournisseurServiceDTO == null ) {
            return null;
        }

        FournisseurService fournisseurService = new FournisseurService();

        fournisseurService.setId( fournisseurServiceDTO.getId() );
        fournisseurService.setNom( fournisseurServiceDTO.getNom() );
        fournisseurService.setDescription( fournisseurServiceDTO.getDescription() );

        return fournisseurService;
    }

    protected Rolee roleeDTOToRolee(RoleeDTO roleeDTO) {
        if ( roleeDTO == null ) {
            return null;
        }

        Rolee rolee = new Rolee();

        rolee.setId( roleeDTO.getId() );
        rolee.setNom( roleeDTO.getNom() );
        rolee.setDescription( roleeDTO.getDescription() );
        rolee.fs( fournisseurServiceDTOToFournisseurService( roleeDTO.getFs() ) );

        return rolee;
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

    protected void fournisseurServiceDTOToFournisseurService1(FournisseurServiceDTO fournisseurServiceDTO, FournisseurService mappingTarget) {
        if ( fournisseurServiceDTO == null ) {
            return;
        }

        if ( fournisseurServiceDTO.getId() != null ) {
            mappingTarget.setId( fournisseurServiceDTO.getId() );
        }
        if ( fournisseurServiceDTO.getNom() != null ) {
            mappingTarget.setNom( fournisseurServiceDTO.getNom() );
        }
        if ( fournisseurServiceDTO.getDescription() != null ) {
            mappingTarget.setDescription( fournisseurServiceDTO.getDescription() );
        }
    }

    protected void roleeDTOToRolee1(RoleeDTO roleeDTO, Rolee mappingTarget) {
        if ( roleeDTO == null ) {
            return;
        }

        if ( roleeDTO.getId() != null ) {
            mappingTarget.setId( roleeDTO.getId() );
        }
        if ( roleeDTO.getNom() != null ) {
            mappingTarget.setNom( roleeDTO.getNom() );
        }
        if ( roleeDTO.getDescription() != null ) {
            mappingTarget.setDescription( roleeDTO.getDescription() );
        }
        if ( roleeDTO.getFs() != null ) {
            if ( mappingTarget.getFs() == null ) {
                mappingTarget.fs( new FournisseurService() );
            }
            fournisseurServiceDTOToFournisseurService1( roleeDTO.getFs(), mappingTarget.getFs() );
        }
    }
}
