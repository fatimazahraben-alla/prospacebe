package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

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

    public Long getGestionnaireEspaceProId() {
        return gestionnaireEspaceProId;
    }

    public void setGestionnaireEspaceProId(Long gestionnaireEspaceProId) {
        this.gestionnaireEspaceProId = gestionnaireEspaceProId;
    }

    public String getGestionnaireEspaceProIdentifiant() {
        return gestionnaireEspaceProIdentifiant;
    }

    public void setGestionnaireEspaceProIdentifiant(String gestionnaireEspaceProIdentifiant) {
        this.gestionnaireEspaceProIdentifiant = gestionnaireEspaceProIdentifiant;
    }

    public Long getUtilisateurProId() {
        return utilisateurProId;
    }

    public void setUtilisateurProId(Long utilisateurProId) {
        this.utilisateurProId = utilisateurProId;
    }

    public String getUtilisateurProIdentifiant() {
        return utilisateurProIdentifiant;
    }

    public void setUtilisateurProIdentifiant(String utilisateurProIdentifiant) {
        this.utilisateurProIdentifiant = utilisateurProIdentifiant;
    }

    private Long gestionnaireEspaceProId;
    private String gestionnaireEspaceProIdentifiant;
    private Long utilisateurProId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcurationDTO)) {
            return false;
        }

        ProcurationDTO procuration = (ProcurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, procuration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcurationDTO{" +
                "id=" + getId() +
                ", nom='" + getNom() + "'" +
                ", dateEffet='" + getDateEffet() + "'" +
                ", dateFin='" + getDateFin() + "'" +
                ", gestionnaireEspacePro=" + getGestionnaireEspaceProId() + getGestionnaireEspaceProIdentifiant()+
                ", utilisateurPro=" + getUtilisateurProId() + getUtilisateurProIdentifiant()+
                "}";
    }
}
