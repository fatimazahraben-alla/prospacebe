package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Entreprise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the Entreprise entity.
 */

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, UUID> {
}
