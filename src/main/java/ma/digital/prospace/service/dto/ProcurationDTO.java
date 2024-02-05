package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ProcurationDTO} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcurationDTO implements Serializable {

    private Long id;

    private Instant dateEffet;

    private Instant dateFin;

    private CompteProDTO gestionnaireEspacePro;

    private CompteProDTO utilisateurPro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", gestionnaireEspacePro=" + getGestionnaireEspacePro() +
            ", utilisateurPro=" + getUtilisateurPro() +
            "}";
    }
}
