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

  // @Query("SELECT COUNT(p) FROM Procuration p " +
      //     "WHERE p.utilisateurPro.id = :connected_account " +
      //     "AND p.gestionnaireEspacePro IS NOT NULL")
  @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Procuration p " +
          "WHERE p.utilisateurPro.id = :compteId " +
          "AND p.gestionnaireEspacePro.id = :connectedAccount")
  boolean checkProcurationForCompteAndGestionnaire(Long compteId, Long connectedAccount);


  Procuration findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(Long utilisateurProId, Long gestionnaireEspaceProId);



}
