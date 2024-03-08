package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    Association findByFsAndCompteID(Long fs, Long compteId);
    List<Entreprise> getListEntreprisesByCompteAndFs(Long compteId, Long fs);
}

