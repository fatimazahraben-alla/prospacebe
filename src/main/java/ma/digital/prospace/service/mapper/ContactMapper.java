package ma.digital.prospace.service.mapper;



import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.service.dto.ContactDTO;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(target = "COMPID", source = "comptePro", qualifiedByName = "compteId")
    ContactDTO toDto(Contact contact);

    @Named("compteId")
    default Long mapCompteProToId(ComptePro compte) {
        return compte != null ? compte.getId() : null;
    }

    @Named("compteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactDTO toDtoCompteId(Contact Contact);

    // Contact toEntity(ContactDTO contactDTO);
}