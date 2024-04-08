package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Procuration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the Procuration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcurationRepository extends JpaRepository<Procuration, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Procuration p " +
            "WHERE p.utilisateurPro.id = :compteId " +
            "AND p.gestionnaireEspacePro.id = :connectedAccount")
    boolean checkProcurationForCompteAndGestionnaire(UUID compteId, UUID connectedAccount);


    Procuration findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(UUID utilisateurProId, UUID gestionnaireEspaceProId);
}
