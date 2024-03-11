package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.ProcurationDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-10T19:12:02+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ProcurationMapperImpl implements ProcurationMapper {

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
    public Procuration toEntity(ProcurationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Procuration procuration = new Procuration();

        procuration.setDateEffet( mapLocalDateToInstant( dto.getDateEffet() ) );
        procuration.setDateFin( mapLocalDateToInstant( dto.getDateFin() ) );
        procuration.setId( dto.getId() );
        procuration.gestionnaireEspacePro( compteProDTOToComptePro( dto.getGestionnaireEspacePro() ) );
        procuration.utilisateurPro( compteProDTOToComptePro( dto.getUtilisateurPro() ) );

        return procuration;
    }

    @Override
    public ProcurationDTO toDto(Procuration entity) {
        if ( entity == null ) {
            return null;
        }

        ProcurationDTO procurationDTO = new ProcurationDTO();

        procurationDTO.setDateEffet( mapInstantToLocalDate( entity.getDateEffet() ) );
        procurationDTO.setDateFin( mapInstantToLocalDate( entity.getDateFin() ) );
        procurationDTO.setId( entity.getId() );
        procurationDTO.setGestionnaireEspacePro( compteProToCompteProDTO( entity.getGestionnaireEspacePro() ) );
        procurationDTO.setUtilisateurPro( compteProToCompteProDTO( entity.getUtilisateurPro() ) );

        return procurationDTO;
    }

    protected ComptePro compteProDTOToComptePro(CompteProDTO compteProDTO) {
        if ( compteProDTO == null ) {
            return null;
        }

        ComptePro comptePro = new ComptePro();

        comptePro.setId( compteProDTO.getId() );

        return comptePro;
    }

    protected CompteProDTO compteProToCompteProDTO(ComptePro comptePro) {
        if ( comptePro == null ) {
            return null;
        }

        CompteProDTO compteProDTO = new CompteProDTO();

        compteProDTO.setId( comptePro.getId() );

        return compteProDTO;
    }
}
