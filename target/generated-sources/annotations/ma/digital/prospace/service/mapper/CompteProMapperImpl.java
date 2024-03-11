package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.service.dto.CompteProDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-11T00:15:04+0000",
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

        return comptePro;
    }

    @Override
    public CompteProDTO toDto(ComptePro entity) {
        if ( entity == null ) {
            return null;
        }

        CompteProDTO compteProDTO = new CompteProDTO();

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
    }
}
