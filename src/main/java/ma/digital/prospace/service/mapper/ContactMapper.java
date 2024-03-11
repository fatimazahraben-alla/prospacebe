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

@Mapper(componentModel = "spring", uses = CompteProMapper.class)
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {


    @Named("compteId")
    default Long mapCompteProToId(ComptePro compte) {
        return compte != null ? compte.getId() : null;
    }

    // Utilisez un autre nom pour cette méthode, car le nom "compteId" est déjà utilisé.
    @Named("toDtoCompteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "COMPID", source = "comptePro", qualifiedByName = "compteId")
    ContactDTO toDtoCompteId(Contact contact);

    // Contact toEntity(ContactDTO contactDTO);
}