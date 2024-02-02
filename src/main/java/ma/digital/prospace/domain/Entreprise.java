package ma.digital.prospace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "statut_juridique")
    private String statutJuridique;

    @Column(name = "tribunal")
    private String tribunal;

    @Column(name = "numero_rc")
    private String numeroRC;

    @Column(name = "ice")
    private String ice;

    @Column(name = "activite")
    private String activite;

    @Column(name = "forme_juridique")
    private String formeJuridique;

    @Column(name = "date_immatriculation")
    private Instant dateImmatriculation;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "entrepriseGeree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entrepriseGeree", "mandataires", "mandants", "associations" }, allowSetters = true)
    private Set<ComptePro> gerants = new HashSet<>();

    @OneToMany(mappedBy = "entreprise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entreprise", "compte", "role" }, allowSetters = true)
    private Set<Association> associations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public Entreprise denomination(String denomination) {
        this.setDenomination(denomination);
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getStatutJuridique() {
        return this.statutJuridique;
    }

    public Entreprise statutJuridique(String statutJuridique) {
        this.setStatutJuridique(statutJuridique);
        return this;
    }

    public void setStatutJuridique(String statutJuridique) {
        this.statutJuridique = statutJuridique;
    }

    public String getTribunal() {
        return this.tribunal;
    }

    public Entreprise tribunal(String tribunal) {
        this.setTribunal(tribunal);
        return this;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getNumeroRC() {
        return this.numeroRC;
    }

    public Entreprise numeroRC(String numeroRC) {
        this.setNumeroRC(numeroRC);
        return this;
    }

    public void setNumeroRC(String numeroRC) {
        this.numeroRC = numeroRC;
    }

    public String getIce() {
        return this.ice;
    }

    public Entreprise ice(String ice) {
        this.setIce(ice);
        return this;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getActivite() {
        return this.activite;
    }

    public Entreprise activite(String activite) {
        this.setActivite(activite);
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getFormeJuridique() {
        return this.formeJuridique;
    }

    public Entreprise formeJuridique(String formeJuridique) {
        this.setFormeJuridique(formeJuridique);
        return this;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public Instant getDateImmatriculation() {
        return this.dateImmatriculation;
    }

    public Entreprise dateImmatriculation(Instant dateImmatriculation) {
        this.setDateImmatriculation(dateImmatriculation);
        return this;
    }

    public void setDateImmatriculation(Instant dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public String getEtat() {
        return this.etat;
    }

    public Entreprise etat(String etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<ComptePro> getGerants() {
        return this.gerants;
    }

    public void setGerants(Set<ComptePro> comptePros) {
        if (this.gerants != null) {
            this.gerants.forEach(i -> i.setEntrepriseGeree(null));
        }
        if (comptePros != null) {
            comptePros.forEach(i -> i.setEntrepriseGeree(this));
        }
        this.gerants = comptePros;
    }

    public Entreprise gerants(Set<ComptePro> comptePros) {
        this.setGerants(comptePros);
        return this;
    }

    public Entreprise addGerants(ComptePro comptePro) {
        this.gerants.add(comptePro);
        comptePro.setEntrepriseGeree(this);
        return this;
    }

    public Entreprise removeGerants(ComptePro comptePro) {
        this.gerants.remove(comptePro);
        comptePro.setEntrepriseGeree(null);
        return this;
    }

    public Set<Association> getAssociations() {
        return this.associations;
    }

    public void setAssociations(Set<Association> associations) {
        if (this.associations != null) {
            this.associations.forEach(i -> i.setEntreprise(null));
        }
        if (associations != null) {
            associations.forEach(i -> i.setEntreprise(this));
        }
        this.associations = associations;
    }

    public Entreprise associations(Set<Association> associations) {
        this.setAssociations(associations);
        return this;
    }

    public Entreprise addAssociations(Association association) {
        this.associations.add(association);
        association.setEntreprise(this);
        return this;
    }

    public Entreprise removeAssociations(Association association) {
        this.associations.remove(association);
        association.setEntreprise(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", denomination='" + getDenomination() + "'" +
            ", statutJuridique='" + getStatutJuridique() + "'" +
            ", tribunal='" + getTribunal() + "'" +
            ", numeroRC='" + getNumeroRC() + "'" +
            ", ice='" + getIce() + "'" +
            ", activite='" + getActivite() + "'" +
            ", formeJuridique='" + getFormeJuridique() + "'" +
            ", dateImmatriculation='" + getDateImmatriculation() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
