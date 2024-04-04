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
   // Optional<ComptePro> findByIdentifiant(String identifiant);
    ComptePro getById(Long id);
    ComptePro findByAndPrenomFr(String loginuser);
  //  @Query("SELECT p.gestionnaireEspacePro FROM Procuration p " +
        //    "WHERE p.utilisateurPro.id = :connected_account")
 //   ComptePro findGestionnaireForUtilisateur(String connected_account);

}
