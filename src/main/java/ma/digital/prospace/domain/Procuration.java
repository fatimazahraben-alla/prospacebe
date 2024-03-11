package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Procuration.
 */
@Entity
@Data
@Table(name = "procuration")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Procuration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_effet")
    private Date dateEffet;

    @Column(name = "date_fin")
    private Date dateFin;

    @Transient
    @ManyToOne
    private ComptePro gestionnaireEspacePro;
@Transient
    @ManyToOne
    private ComptePro utilisateurPro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Procuration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateEffet() {
        return this.dateEffet;
    }

    public Procuration dateEffet(Instant dateEffet) {
        this.setDateEffet(dateEffet);
        return this;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public Procuration dateFin(Instant dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public ComptePro getGestionnaireEspacePro() {
        return this.gestionnaireEspacePro;
    }

    public void setGestionnaireEspacePro(ComptePro comptePro) {
        this.gestionnaireEspacePro = comptePro;
    }

    public Procuration gestionnaireEspacePro(ComptePro comptePro) {
        this.setGestionnaireEspacePro(comptePro);
        return this;
    }

    public ComptePro getUtilisateurPro() {
        return this.utilisateurPro;
    }

    public void setUtilisateurPro(ComptePro comptePro) {
        this.utilisateurPro = comptePro;
    }

    public Procuration utilisateurPro(ComptePro comptePro) {
        this.setUtilisateurPro(comptePro);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procuration)) {
            return false;
        }
        return id != null && id.equals(((Procuration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Procuration{" +
            "id=" + getId() +
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
