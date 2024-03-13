package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Rolee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;

import java.util.List;

/**
 * Spring Data JPA repository for the Rolee entity.
 */
@Repository
public interface RoleeRepository extends JpaRepository<Rolee, Long> {
}
