package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {


}
