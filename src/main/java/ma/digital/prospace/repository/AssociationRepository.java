package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Entreprise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Association entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    @Query("SELECT a FROM Association a WHERE a.fs = :fs AND a.compte.id = :compteId")
    Association findByFsAndCompteID(Long fs, Long compteID);
    @Query("SELECT e FROM Entreprise e JOIN e.associations a JOIN a.compte c WHERE c.id = :compteID AND a.fs = :fs")
    List<Entreprise> getListEntreprisesByCompteAndFs(Long compteID, Long fs);


}
