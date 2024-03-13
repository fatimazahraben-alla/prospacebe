package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
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

    private LocalDate dateEffet;

    private LocalDate dateFin;

    private CompteProDTO gestionnaireEspacePro;

    private CompteProDTO utilisateurPro;

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

    public LocalDate getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(LocalDate dateEffet) {
        this.dateEffet = dateEffet;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public CompteProDTO getGestionnaireEspacePro() {
        return gestionnaireEspacePro;
    }

    public void setGestionnaireEspacePro(CompteProDTO gestionnaireEspacePro) {
        this.gestionnaireEspacePro = gestionnaireEspacePro;
    }

    public CompteProDTO getUtilisateurPro() {
        return utilisateurPro;
    }

    public void setUtilisateurPro(CompteProDTO utilisateurPro) {
        this.utilisateurPro = utilisateurPro;
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
                ", gestionnaireEspacePro=" + getGestionnaireEspacePro() +
                ", utilisateurPro=" + getUtilisateurPro() +
                "}";
    }
}
