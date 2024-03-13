package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByCompteProId(Long compteProId);
}
