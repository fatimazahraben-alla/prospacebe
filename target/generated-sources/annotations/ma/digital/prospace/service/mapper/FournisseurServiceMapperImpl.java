package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-13T14:51:47+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class FournisseurServiceMapperImpl implements FournisseurServiceMapper {

    @Override
    public FournisseurService toEntity(FournisseurServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FournisseurService fournisseurService = new FournisseurService();

        fournisseurService.setId( dto.getId() );
        fournisseurService.setNom( dto.getNom() );
        fournisseurService.setDescription( dto.getDescription() );

        return fournisseurService;
    }

    @Override
    public FournisseurServiceDTO toDto(FournisseurService entity) {
        if ( entity == null ) {
            return null;
        }

        FournisseurServiceDTO fournisseurServiceDTO = new FournisseurServiceDTO();

        fournisseurServiceDTO.setId( entity.getId() );
        fournisseurServiceDTO.setNom( entity.getNom() );
        fournisseurServiceDTO.setDescription( entity.getDescription() );

        return fournisseurServiceDTO;
    }

    @Override
    public List<FournisseurService> toEntity(List<FournisseurServiceDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FournisseurService> list = new ArrayList<FournisseurService>( dtoList.size() );
        for ( FournisseurServiceDTO fournisseurServiceDTO : dtoList ) {
            list.add( toEntity( fournisseurServiceDTO ) );
        }

        return list;
    }

    @Override
    public List<FournisseurServiceDTO> toDto(List<FournisseurService> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FournisseurServiceDTO> list = new ArrayList<FournisseurServiceDTO>( entityList.size() );
        for ( FournisseurService fournisseurService : entityList ) {
            list.add( toDto( fournisseurService ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(FournisseurService entity, FournisseurServiceDTO dto) {
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
}
