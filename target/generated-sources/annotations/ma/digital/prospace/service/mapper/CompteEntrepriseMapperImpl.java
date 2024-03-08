package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.CompteEntreprise;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T12:23:49+0100",
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
        compteEntreprise.setEntreprise( dto.getEntreprise() );
        compteEntreprise.setCompte( dto.getCompte() );
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
        compteEntrepriseDTO.setEntreprise( entity.getEntreprise() );
        compteEntrepriseDTO.setCompte( entity.getCompte() );
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
            entity.setEntreprise( dto.getEntreprise() );
        }
        if ( dto.getCompte() != null ) {
            entity.setCompte( dto.getCompte() );
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
}
