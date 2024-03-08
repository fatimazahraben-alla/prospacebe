package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the ComptePro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteProRepository extends JpaRepository<ComptePro, Long> {
    Optional<ComptePro> findById(Long id);
}
