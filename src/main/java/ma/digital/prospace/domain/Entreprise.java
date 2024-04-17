package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Transient
    @Column(name = "denomination")
    private String denomination;

    @Transient
    @Column(name = "statut_juridique")
    private String statutJuridique;

    @Transient
    @Column(name = "tribunal")
    private String tribunal;
    @Transient
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Status_Perphysique_Permorale", length = 50, nullable = true)
    private Statut Status_Perphysique_Permorale;

    @Transient
    @Column(name = "numero_rc")
    private String numeroRC;

    @Transient
    @Column(name = "ice")
    private String ice;

    @Transient
    @Column(name = "activite")
    private String activite;

    @Transient
    @Column(name = "forme_juridique")
    private String formeJuridique;

    @Transient
    @Column(name = "date_immatriculation")
    private Date dateImmatriculation;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "entrepriseGeree")
    @JsonIgnoreProperties(value = { "entrepriseGeree", "mandataires", "mandants", "associations" }, allowSetters = true)
    private Set<ComptePro> gerants = new HashSet<>();

    @OneToMany(mappedBy = "entreprise") // Correspond à la propriété dans la classe Association
    @JsonIgnoreProperties(value = { "entreprise", "compte", "role" }, allowSetters = true)
    private Set<Association> associations = new HashSet<>();

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getStatutJuridique() {
        return this.statutJuridique;
    }

    public void setStatutJuridique(String statutJuridique) {
        this.statutJuridique = statutJuridique;
    }

    public String getTribunal() {
        return this.tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getNumeroRC() {
        return this.numeroRC;
    }

    public void setNumeroRC(String numeroRC) {
        this.numeroRC = numeroRC;
    }

    public String getIce() {
        return this.ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getActivite() {
        return this.activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getFormeJuridique() {
        return this.formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }


    public Date getDateImmatriculation() {
        return dateImmatriculation;
    }

    public void setDateImmatriculation(Date dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public String getEtat() {
        return etat;
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

    public Statut getStatus_Perphysique_Permorale() {
        return Status_Perphysique_Permorale;
    }

    public void setStatus_Perphysique_Permorale(Statut status_Perphysique_Permorale) {
        Status_Perphysique_Permorale = status_Perphysique_Permorale;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                ", statutJuridique='" + statutJuridique + '\'' +
                ", tribunal='" + tribunal + '\'' +
                ", Status_Perphysique_Permorale=" + Status_Perphysique_Permorale +
                ", numeroRC='" + numeroRC + '\'' +
                ", ice='" + ice + '\'' +
                ", activite='" + activite + '\'' +
                ", formeJuridique='" + formeJuridique + '\'' +
                ", dateImmatriculation=" + dateImmatriculation +
                ", etat='" + etat + '\'' +
                ", gerants=" + gerants +
                ", associations=" + associations +
                '}';
    }

}
