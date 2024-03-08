package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T11:37:15+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RoleeMapperImpl implements RoleeMapper {

    @Override
    public Rolee toEntity(RoleeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Rolee rolee = new Rolee();

        rolee.setId( dto.getId() );
        rolee.setNom( dto.getNom() );
        rolee.setDescription( dto.getDescription() );

        return rolee;
    }

    @Override
    public List<Rolee> toEntity(List<RoleeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Rolee> list = new ArrayList<Rolee>( dtoList.size() );
        for ( RoleeDTO roleeDTO : dtoList ) {
            list.add( toEntity( roleeDTO ) );
        }

        return list;
    }

    @Override
    public List<RoleeDTO> toDto(List<Rolee> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleeDTO> list = new ArrayList<RoleeDTO>( entityList.size() );
        for ( Rolee rolee : entityList ) {
            list.add( toDto( rolee ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Rolee entity, RoleeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }

    @Override
    public RoleeDTO toDto(Rolee s) {
        if ( s == null ) {
            return null;
        }

        RoleeDTO roleeDTO = new RoleeDTO();

        roleeDTO.setId( s.getId() );
        roleeDTO.setNom( s.getNom() );
        roleeDTO.setDescription( s.getDescription() );

        roleeDTO.setFournisseurID( s.getFs() != null ? s.getFs().getId() : null );

        return roleeDTO;
    }

    @Override
    public FournisseurServiceDTO toDTOFournisseurService(FournisseurService fournisseurService) {
        if ( fournisseurService == null ) {
            return null;
        }

        FournisseurServiceDTO fournisseurServiceDTO = new FournisseurServiceDTO();

        fournisseurServiceDTO.setId( fournisseurService.getId() );
        fournisseurServiceDTO.setNom( fournisseurService.getNom() );
        fournisseurServiceDTO.setDescription( fournisseurService.getDescription() );

        return fournisseurServiceDTO;
    }
}
