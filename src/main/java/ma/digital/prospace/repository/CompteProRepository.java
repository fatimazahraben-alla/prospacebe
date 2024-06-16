package ma.digital.prospace.repository;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the ComptePro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteProRepository extends JpaRepository<ComptePro, String> {

    Optional<ComptePro> findById(String Id);
    @Query(value = "SELECT * FROM compte_pro WHERE id = :id", nativeQuery = true)
    Optional<ComptePro> findByCustomIdQuery(String id);
    @Query("SELECT c FROM ComptePro c WHERE c.id = :userId OR c.id IN (SELECT p.utilisateurPro.id FROM Procuration p WHERE p.gestionnaireEspacePro.id = :userId)")
    List<ComptePro> findAllRelatedByUser(@Param("userId") String userId);
    @Query("SELECT e FROM Entreprise e JOIN e.gerants g WHERE g.id = :compteProId")
    List<Entreprise> findEntreprisesByGerants(@Param("compteProId") String compteProId);

    ComptePro findCompteProById(String compte);
    @Query("SELECT e.id FROM Entreprise e JOIN e.associations a WHERE a.compte.id = :compteProId AND a.statut = 'ACCEPTED'")
    List<String> findAllEntrepriseIdsByCompteProId(@Param("compteProId") String compteProId);

}