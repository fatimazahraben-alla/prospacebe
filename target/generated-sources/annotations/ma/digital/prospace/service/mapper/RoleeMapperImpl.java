package ma.digital.prospace.service.mapper;

import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-13T14:51:48+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class RoleeMapperImpl implements RoleeMapper {

    @Override
    public RoleeDTO toDto(Rolee rolee) {
        if ( rolee == null ) {
            return null;
        }

        RoleeDTO roleeDTO = new RoleeDTO();

        roleeDTO.setFournisseurServiceId( roleeFsId( rolee ) );
        roleeDTO.setId( rolee.getId() );
        roleeDTO.setNom( rolee.getNom() );
        roleeDTO.setDescription( rolee.getDescription() );

        return roleeDTO;
    }

    @Override
    public Rolee toEntity(RoleeDTO roleeDTO) {
        if ( roleeDTO == null ) {
            return null;
        }

        Rolee rolee = new Rolee();

        rolee.setFs( map( roleeDTO.getFournisseurServiceId() ) );
        rolee.setId( roleeDTO.getId() );
        rolee.setNom( roleeDTO.getNom() );
        rolee.setDescription( roleeDTO.getDescription() );

        return rolee;
    }

    private Long roleeFsId(Rolee rolee) {
        if ( rolee == null ) {
            return null;
        }
        FournisseurService fs = rolee.getFs();
        if ( fs == null ) {
            return null;
        }
        Long id = fs.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
