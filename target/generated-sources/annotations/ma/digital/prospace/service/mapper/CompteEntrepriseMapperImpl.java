package ma.digital.prospace.service.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.CompteEntreprise;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-13T14:51:48+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CompteEntrepriseMapperImpl implements CompteEntrepriseMapper {

    @Override
    public CompteEntreprise toEntity(CompteEntrepriseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CompteEntreprise compteEntreprise = new CompteEntreprise();

        compteEntreprise.setId( dto.getId() );
        compteEntreprise.setEntreprise( entrepriseDTOToEntreprise( dto.getEntreprise() ) );
        List<String> list = dto.getRoles();
        if ( list != null ) {
            compteEntreprise.setRoles( new LinkedHashSet<String>( list ) );
        }

        return compteEntreprise;
    }

    @Override
    public CompteEntrepriseDTO toDto(CompteEntreprise entity) {
        if ( entity == null ) {
            return null;
        }

        CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();

        compteEntrepriseDTO.setId( entity.getId() );
        compteEntrepriseDTO.setEntreprise( entrepriseToEntrepriseDTO( entity.getEntreprise() ) );
        Set<String> set = entity.getRoles();
        if ( set != null ) {
            compteEntrepriseDTO.setRoles( new ArrayList<String>( set ) );
        }

        return compteEntrepriseDTO;
    }

    @Override
    public List<CompteEntreprise> toEntity(List<CompteEntrepriseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CompteEntreprise> list = new ArrayList<CompteEntreprise>( dtoList.size() );
        for ( CompteEntrepriseDTO compteEntrepriseDTO : dtoList ) {
            list.add( toEntity( compteEntrepriseDTO ) );
        }

        return list;
    }

    @Override
    public List<CompteEntrepriseDTO> toDto(List<CompteEntreprise> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CompteEntrepriseDTO> list = new ArrayList<CompteEntrepriseDTO>( entityList.size() );
        for ( CompteEntreprise compteEntreprise : entityList ) {
            list.add( toDto( compteEntreprise ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CompteEntreprise entity, CompteEntrepriseDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getEntreprise() != null ) {
            if ( entity.getEntreprise() == null ) {
                entity.setEntreprise( new Entreprise() );
            }
            entrepriseDTOToEntreprise1( dto.getEntreprise(), entity.getEntreprise() );
        }
        if ( entity.getRoles() != null ) {
            List<String> list = dto.getRoles();
            if ( list != null ) {
                entity.getRoles().clear();
                entity.getRoles().addAll( list );
            }
        }
        else {
            List<String> list = dto.getRoles();
            if ( list != null ) {
                entity.setRoles( new LinkedHashSet<String>( list ) );
            }
        }
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
        if ( entrepriseDTO.getDateImmatriculation() != null ) {
            entreprise.setDateImmatriculation( Instant.parse( entrepriseDTO.getDateImmatriculation() ) );
        }
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
            mappingTarget.setDateImmatriculation( Instant.parse( entrepriseDTO.getDateImmatriculation() ) );
        }
        if ( entrepriseDTO.getEtat() != null ) {
            mappingTarget.setEtat( entrepriseDTO.getEtat() );
        }
    }
}
