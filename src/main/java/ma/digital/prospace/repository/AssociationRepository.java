package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.FournisseurService;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Association entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    @Query("SELECT a FROM Association a WHERE a.role.fs.id = :fsId AND a.compte.id = :compteId")
    List<Association> findAllByFsAndCompteID(@Param("fsId") Long fsId, @Param("compteId") Long compteId);



    @Query("SELECT DISTINCT e FROM Entreprise e JOIN e.associations a WHERE a.compte.id = :compteID AND a.role.fs.id = :fsId")
    List<Entreprise> findAllDistinctByCompteIdAndRoleFsId(@Param("fsId") Long fsId, @Param("compteID") Long compteID);



}
