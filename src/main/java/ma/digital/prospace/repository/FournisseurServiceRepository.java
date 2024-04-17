package ma.digital.prospace.repository;

import ma.digital.prospace.domain.FournisseurService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the FournisseurService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurServiceRepository extends JpaRepository<FournisseurService, UUID> {
}

