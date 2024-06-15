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
    List<Association> findByCompteIdAndStatutAndRoleNom(String compteId, StatutAssociation statut, String roleNom);
    List<Association> findAllById(Iterable<UUID> ids);
    List<Association> findByCompteInitiateurID(String compteInitiateurID);
    List<Association> findByCompteInitiateurIDIn(List<String> compteInitiateurIDs);
    @Query("SELECT p.utilisateurPro.id FROM Procuration p WHERE p.gestionnaireEspacePro.id = :managerID AND p.statut = 'ACCEPTED'")
    List<String> findAllAcceptedDelegatesByManagerId(@Param("managerID") String managerID);
    @Query("SELECT a.compte.id FROM Association a WHERE a.entreprise.id = :entrepriseID AND a.role.nom = 'GESTIONNAIRE_ENTREPRISE' AND a.statut = 'ACCEPTED'")
    List<String> findAcceptedEnterpriseManagersByEntrepriseId(@Param("entrepriseID") String entrepriseID);
}

