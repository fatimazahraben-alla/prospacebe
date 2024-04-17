package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the ComptePro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteProRepository extends JpaRepository<ComptePro, String> {
    Optional<ComptePro> findByIdentifiant(String identifiant);
    Optional<ComptePro> findById(String Id);

    @Query(value = "SELECT * FROM compte_pro WHERE id = :id", nativeQuery = true)
    Optional<ComptePro> findByCustomIdQuery(String id);


}
