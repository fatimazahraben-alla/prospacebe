package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Rolee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rolee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleeRepository extends JpaRepository<Rolee, Long> {}
