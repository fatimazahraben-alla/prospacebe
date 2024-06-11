package ma.digital.prospace.repository;

import ma.digital.prospace.domain.AuditAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuditAssociationRepository extends JpaRepository<AuditAssociation, UUID> {
    List<AuditAssociation> findByCompteId(String compteId);
}
