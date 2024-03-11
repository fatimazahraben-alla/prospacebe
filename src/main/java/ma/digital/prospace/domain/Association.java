package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ma.digital.prospace.domain.enumeration.StatutAssociation;

/**
 * A Association.
 */
@Entity
@Data
@Table(name = "association")
public class Association implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_effet", nullable = false)
    private Instant dateEffet;
    @NotNull
    @Column(name = "date_fin")
    private Instant dateFin;
    @NotNull
    @Column(name = "mail")
    private String mail;
    @NotNull
    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatutAssociation statut;

    @ManyToOne
    @Column(name = "entreprise")
    private Entreprise entreprise;

    @ManyToOne
    @Column(name = "compte")
    private ComptePro compte;

    @ManyToOne
    @Column(name = "role")
    private Rolee role;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Association id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateEffet() {
        return this.dateEffet;
    }

    public Association dateEffet(Instant dateEffet) {
        this.setDateEffet(dateEffet);
        return this;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public Association dateFin(Instant dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getMail() {
        return this.mail;
    }

    public Association mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Association telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutAssociation getStatut() {
        return this.statut;
    }

    public Association statut(StatutAssociation statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Association entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public ComptePro getCompte() {
        return this.compte;
    }

    public void setCompte(ComptePro comptePro) {
        this.compte = comptePro;
    }

    public Association compte(ComptePro comptePro) {
        this.setCompte(comptePro);
        return this;
    }

    public Rolee getRole() {
        return this.role;
    }

    public void setRole(Rolee rolee) {
        this.role = rolee;
    }

    public Association role(Rolee rolee) {
        this.setRole(rolee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Association)) {
            return false;
        }
        return id != null && id.equals(((Association) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Association{" +
            "id=" + getId() +
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", mail='" + getMail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
