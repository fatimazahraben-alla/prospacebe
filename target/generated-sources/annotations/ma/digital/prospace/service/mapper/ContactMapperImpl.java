package ma.digital.prospace.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.service.dto.ContactDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-11T11:34:04+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact toEntity(ContactDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setDeviceToken( dto.getDeviceToken() );
        contact.setDeviceOS( dto.getDeviceOS() );
        contact.setDeviceVersion( dto.getDeviceVersion() );

        return contact;
    }

    @Override
    public ContactDTO toDto(Contact entity) {
        if ( entity == null ) {
            return null;
        }

        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setDeviceToken( entity.getDeviceToken() );
        contactDTO.setDeviceOS( entity.getDeviceOS() );
        contactDTO.setDeviceVersion( entity.getDeviceVersion() );

        return contactDTO;
    }

    @Override
    public List<Contact> toEntity(List<ContactDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Contact> list = new ArrayList<Contact>( dtoList.size() );
        for ( ContactDTO contactDTO : dtoList ) {
            list.add( toEntity( contactDTO ) );
        }

        return list;
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( entityList.size() );
        for ( Contact contact : entityList ) {
            list.add( toDto( contact ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Contact entity, ContactDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDeviceToken() != null ) {
            entity.setDeviceToken( dto.getDeviceToken() );
        }
        if ( dto.getDeviceOS() != null ) {
            entity.setDeviceOS( dto.getDeviceOS() );
        }
        if ( dto.getDeviceVersion() != null ) {
            entity.setDeviceVersion( dto.getDeviceVersion() );
        }
    }

    @Override
    public ContactDTO toDtoCompteId(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setCOMPID( mapCompteProToId( contact.getComptePro() ) );

        return contactDTO;
    }
}
