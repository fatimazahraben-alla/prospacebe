package ma.digital.prospace.repository;

import ma.digital.prospace.domain.AuditAssociation;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuditAssociationRepository extends JpaRepository<AuditAssociation, UUID> {
    List<AuditAssociation> findByCompteId(String compteId);
    Optional<AuditAssociation> findFirstByAssociationIdOrderByTimestampAsc(UUID associationId);


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AuditAssociation a " +
            "WHERE a.compteId = :compteID " +
            "AND a.associationId IN (SELECT assoc.id FROM Association assoc WHERE assoc.compte.id = :compteInitiateurID " +
            "AND assoc.role.nom = :roleNom AND assoc.statut = :statut)")
    boolean existsByCompteIdAndAssociationIdAndAssociationRoleNomAndAssociationStatut(
            @Param("compteInitiateurID") String compteInitiateurID,
            @Param("compteID") String compteID,
            @Param("roleNom") String roleNom,
            @Param("statut") StatutAssociation statut);
}
