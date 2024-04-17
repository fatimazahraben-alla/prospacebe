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
public interface ProcurationRepository extends JpaRepository<Procuration, UUID> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Procuration p " +
            "WHERE p.utilisateurPro.id = :compteId " +
            "AND p.gestionnaireEspacePro.id = :connectedAccount")
    boolean checkProcurationForCompteAndGestionnaire(String compteId, String connectedAccount);


    Procuration findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(String utilisateurProId, String gestionnaireEspaceProId);
}
