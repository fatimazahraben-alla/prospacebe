package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import jakarta.persistence.*;
import lombok.Setter;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "date_effet")
    private Date dateEffet;
    @Column(name = "date_fin")
    private Date dateFin;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telephone")
    private String telephone;
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutAssociation statut;
    @ManyToOne
    @JsonIgnoreProperties(value = { "associations", "comptePro" })
    private Entreprise entreprise;
    @ManyToOne
    private ComptePro compte;
    @ManyToOne
    private Rolee role;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Date getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Date dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutAssociation getStatut() {
        return statut;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }
    public ComptePro getCompte() {
        return compte;
    }

    public void setCompte(ComptePro compte) {
        this.compte = compte;
    }

    public Rolee getRole() {
        return role;
    }

    public void setRole(Rolee role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Association)) return false;
        Association that = (Association) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
