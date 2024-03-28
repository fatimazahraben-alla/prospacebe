package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Procuration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Procuration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcurationRepository extends JpaRepository<Procuration, Long> {
    @Query("SELECT COUNT(p) FROM Procuration p " +
            "WHERE p.utilisateurPro.id = :compteId " +
            "AND p.gestionnaireEspacePro.id = :connected_account")
    boolean checkProcurationForCompteAndGestionnaire(Long compteId,String connected_account );
}
