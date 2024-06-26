package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.GenericGenerator;

/**
 * A Procuration.
 */
@Entity
@Table(name = "procuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Procuration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Transient
    @Column(name = "date_effet")
    private Instant dateEffet;
    @Transient
    @Column(name = "date_fin")
    private Instant dateFin;


    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutInvitation statut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entrepriseGeree", "mandataires", "mandants", "associations" }, allowSetters = true)
    private ComptePro gestionnaireEspacePro;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entrepriseGeree", "mandataires", "mandants", "associations" }, allowSetters = true)
    private ComptePro utilisateurPro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Procuration id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
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
    public StatutInvitation getStatut() {
        return statut;
    }

    public void setStatut(StatutInvitation statut) {
        this.statut = statut;
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
                "id=" + id +
                ", dateEffet=" + dateEffet +
                ", dateFin=" + dateFin +
                ", gestionnaireEspacePro=" + gestionnaireEspacePro +
                ", utilisateurPro=" + utilisateurPro +
                ", statut=" + statut +
                '}';
    }
}
