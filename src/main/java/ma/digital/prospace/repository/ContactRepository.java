package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Contact findByCompteProId(String id);
}
