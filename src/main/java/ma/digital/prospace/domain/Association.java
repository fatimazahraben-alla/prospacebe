package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.NotNull;
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
@Table(name = "association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    private Long fs;
    @NotNull
    @Column(name = "date_effet", nullable = false)
    private Instant dateEffet;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Column(name = "mail")
    private String mail;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutAssociation statut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gerants", "associations" }, allowSetters = true)
    private Entreprise entreprise;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entrepriseGeree", "mandataires", "mandants", "associations" }, allowSetters = true)
    private ComptePro compte;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fs" }, allowSetters = true)
    private Rolee role;

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateEffet() {
        return this.dateEffet;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutAssociation getStatut() {
        return this.statut;
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

    public ComptePro getCompte() {
        return this.compte;
    }

    public void setCompte(ComptePro compte) {
        this.compte = compte;
    }

    public Rolee getRole() {
        return this.role;
    }

    public void setRole(Rolee role) {
        this.role = role;
    }

    // Overridden methods from Object class
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Association)) return false;
        return id != null && id.equals(((Association) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

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
