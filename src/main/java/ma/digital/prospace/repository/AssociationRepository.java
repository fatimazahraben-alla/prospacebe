package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AssociationRepository extends JpaRepository<Association, UUID> {
    @Query("SELECT a FROM Association a WHERE a.role.fs.id = :fsId AND a.compte.id = :compteId")
    List<Association> findAllByFsAndCompteID(@Param("fsId") String fsId, @Param("compteId") String compteId);
    @Query("SELECT r.nom FROM Association a JOIN a.role r WHERE a.compte.id = :compteProId AND a.entreprise.id = :entrepriseId")
    List<String> findRoleNamesByCompteProIdAndEntrepriseId(@Param("compteProId") String compteProId,
                                                           @Param("entrepriseId") String entrepriseId);
    @Query("SELECT DISTINCT e FROM Entreprise e JOIN e.associations a WHERE a.compte.id = :compteID AND a.role.fs.id = :fsId")
    List<Entreprise> findAllDistinctByCompteIdAndRoleFsId(@Param("fsId") String fsId, @Param("compteID") String compteID);

    @Query("SELECT a FROM Association a WHERE a.compte.id = :compteId")
    List<Association> findAllByCompteID(@Param("compteId") String compteId);
    List<Association> findByCompteIdAndEntrepriseId(String compteId, String entrepriseId);
    List<Association> findAllById(Iterable<UUID> ids);


    List<Association> findAssociationsByCompte_IdAndCompteInitiateurID(String Compte,String Compteinit);

    List<Association> findByCompteInitiateurIDIn(Set<String> compteInitiateurIDs);
    @Query("SELECT DISTINCT assoc.compte.id FROM Association assoc " +
            "WHERE assoc.role.nom = 'gestionnaire_entreprise' " +
            "AND assoc.statut = :statut " +
            "AND assoc.id IN (" +
            "    SELECT a.associationId FROM AuditAssociation a " +
            "    WHERE a.compteId = :compteID)")
    List<String> findAcceptedManagersByEntrepriseCompteId(
            @Param("compteID") String compteID,
            @Param("statut") StatutAssociation statut);



}

