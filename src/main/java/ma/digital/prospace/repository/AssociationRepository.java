package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    @Query("SELECT a FROM Association a WHERE a.role.fs.id = :fsId AND a.compte.id = :compteId")
    List<Association> findAllByFsAndCompteID(@Param("fsId") Long fsId, @Param("compteId") UUID compteId);
    @Query("SELECT r.nom FROM Association a JOIN a.role r WHERE a.compte.id = :compteProId AND a.entreprise.id = :entrepriseId")
    List<String> findRoleNamesByCompteProIdAndEntrepriseId(@Param("compteProId") UUID compteProId,
                                                           @Param("entrepriseId") Long entrepriseId);
    @Query("SELECT DISTINCT e FROM Entreprise e JOIN e.associations a WHERE a.compte.id = :compteID AND a.role.fs.id = :fsId")
    List<Entreprise> findAllDistinctByCompteIdAndRoleFsId(@Param("fsId") Long fsId, @Param("compteID") UUID compteID);
}

