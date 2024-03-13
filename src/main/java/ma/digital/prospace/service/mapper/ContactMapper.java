package ma.digital.prospace.service.mapper;



import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.service.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {


    @Mapping(target = "deviceToken", source = "deviceToken")
    @Mapping(target = "deviceOS", source = "deviceOS")
    @Mapping(target = "deviceVersion", source = "deviceVersion")
    ContactDTO toDto(Contact contact);


    @Mapping(target = "deviceToken", source = "deviceToken")
    @Mapping(target = "deviceOS", source = "deviceOS")
    @Mapping(target = "deviceVersion", source = "deviceVersion")
    Contact toEntity(ContactDTO contactDTO);
}
