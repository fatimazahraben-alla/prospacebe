package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.Procuration} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcurationDTO implements Serializable {

    private Long id;

    private String nom;

    private Instant dateEffet;

    private Instant dateFin;

    private UUID gestionnaireEspaceProId;
    private String gestionnaireEspaceProIdentifiant;
    private UUID utilisateurProId;
    private String utilisateurProIdentifiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public UUID getGestionnaireEspaceProId() {
        return gestionnaireEspaceProId;
    }

    public void setGestionnaireEspaceProId(UUID gestionnaireEspaceProId) {
        this.gestionnaireEspaceProId = gestionnaireEspaceProId;
    }

    public String getGestionnaireEspaceProIdentifiant() {
        return gestionnaireEspaceProIdentifiant;
    }

    public void setGestionnaireEspaceProIdentifiant(String gestionnaireEspaceProIdentifiant) {
        this.gestionnaireEspaceProIdentifiant = gestionnaireEspaceProIdentifiant;
    }

    public UUID getUtilisateurProId() {
        return utilisateurProId;
    }

    public void setUtilisateurProId(UUID utilisateurProId) {
        this.utilisateurProId = utilisateurProId;
    }

    public String getUtilisateurProIdentifiant() {
        return utilisateurProIdentifiant;
    }

    public void setUtilisateurProIdentifiant(String utilisateurProIdentifiant) {
        this.utilisateurProIdentifiant = utilisateurProIdentifiant;
    }
}