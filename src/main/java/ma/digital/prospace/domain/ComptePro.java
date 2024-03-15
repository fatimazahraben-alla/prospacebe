package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ma.digital.prospace.domain.enumeration.StatutCompte;

/**
 * A ComptePro.
 */
@Entity
@Table(name = "compte_pro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComptePro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "identifiant", length = 10)
    private String identifiant;


    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "nom_ar", length = 50, nullable = true)
    private String nomAr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "nom_fr", length = 50, nullable = true)
    private String nomFr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "prenom_ar", length = 50, nullable = true)
    private String prenomAr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "prenom_fr", length = 50, nullable = true)
    private String prenomFr;

    @Transient
    @Size(max = 50)
    @Column(name = "adresse", length = 50)
    private String adresse;

    @Transient
    @Lob
    @Column(name = "photo", nullable = true)
    private String photo;

    @Transient
    @Column(name = "mail")
    private String mail;

    @Transient
    @Column(name = "telephone")
    private String telephone;


    @Column(name = "created_at")
    private Instant createdAt;


    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutCompte statut;




    @ManyToOne
    @JsonIgnoreProperties(value = { "gerants", "associations" }, allowSetters = true)
    private Entreprise entrepriseGeree;

    @OneToMany(mappedBy = "gestionnaireEspacePro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gestionnaireEspacePro", "utilisateurPro" }, allowSetters = true)
    private Set<Procuration> mandataires = new HashSet<>();

    @OneToMany(mappedBy = "utilisateurPro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gestionnaireEspacePro", "utilisateurPro" }, allowSetters = true)
    private Set<Procuration> mandants = new HashSet<>();

    @OneToMany(mappedBy = "compte")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entreprise", "compte", "role" }, allowSetters = true)
    private Set<Association> associations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ComptePro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public ComptePro identifiant(String identifiant) {
        this.setIdentifiant(identifiant);
        return this;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }




    public String getNomAr() {
        return this.nomAr;
    }

    public ComptePro nomAr(String nomAr) {
        this.setNomAr(nomAr);
        return this;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getNomFr() {
        return this.nomFr;
    }

    public ComptePro nomFr(String nomFr) {
        this.setNomFr(nomFr);
        return this;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getPrenomAr() {
        return this.prenomAr;
    }

    public ComptePro prenomAr(String prenomAr) {
        this.setPrenomAr(prenomAr);
        return this;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getPrenomFr() {
        return this.prenomFr;
    }

    public ComptePro prenomFr(String prenomFr) {
        this.setPrenomFr(prenomFr);
        return this;
    }

    public void setPrenomFr(String prenomFr) {
        this.prenomFr = prenomFr;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public ComptePro adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return this.photo;
    }

    public ComptePro photo(String photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMail() {
        return this.mail;
    }

    public ComptePro mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public ComptePro telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutCompte getStatut() {
        return this.statut;
    }

    public ComptePro statut(StatutCompte statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutCompte statut) {
        this.statut = statut;
    }

    public Entreprise getEntrepriseGeree() {
        return this.entrepriseGeree;
    }

    public void setEntrepriseGeree(Entreprise entreprise) {
        this.entrepriseGeree = entreprise;
    }

    public ComptePro entrepriseGeree(Entreprise entreprise) {
        this.setEntrepriseGeree(entreprise);
        return this;
    }

    public Set<Procuration> getMandataires() {
        return this.mandataires;
    }

    public void setMandataires(Set<Procuration> procurations) {
        if (this.mandataires != null) {
            this.mandataires.forEach(i -> i.setGestionnaireEspacePro(null));
        }
        if (procurations != null) {
            procurations.forEach(i -> i.setGestionnaireEspacePro(this));
        }
        this.mandataires = procurations;
    }

    public ComptePro mandataires(Set<Procuration> procurations) {
        this.setMandataires(procurations);
        return this;
    }

    public ComptePro addMandataires(Procuration procuration) {
        this.mandataires.add(procuration);
        procuration.setGestionnaireEspacePro(this);
        return this;
    }

    public ComptePro removeMandataires(Procuration procuration) {
        this.mandataires.remove(procuration);
        procuration.setGestionnaireEspacePro(null);
        return this;
    }

    public Set<Procuration> getMandants() {
        return this.mandants;
    }

    public void setMandants(Set<Procuration> procurations) {
        if (this.mandants != null) {
            this.mandants.forEach(i -> i.setUtilisateurPro(null));
        }
        if (procurations != null) {
            procurations.forEach(i -> i.setUtilisateurPro(this));
        }
        this.mandants = procurations;
    }

    public ComptePro mandants(Set<Procuration> procurations) {
        this.setMandants(procurations);
        return this;
    }

    public ComptePro addMandants(Procuration procuration) {
        this.mandants.add(procuration);
        procuration.setUtilisateurPro(this);
        return this;
    }

    public ComptePro removeMandants(Procuration procuration) {
        this.mandants.remove(procuration);
        procuration.setUtilisateurPro(null);
        return this;
    }

    public Set<Association> getAssociations() {
        return this.associations;
    }

    public void setAssociations(Set<Association> associations) {
        if (this.associations != null) {
            this.associations.forEach(i -> i.setCompte(null));
        }
        if (associations != null) {
            associations.forEach(i -> i.setCompte(this));
        }
        this.associations = associations;
    }

    public ComptePro associations(Set<Association> associations) {
        this.setAssociations(associations);
        return this;
    }

    public ComptePro addAssociations(Association association) {
        this.associations.add(association);
        association.setCompte(this);
        return this;
    }

    public ComptePro removeAssociations(Association association) {
        this.associations.remove(association);
        association.setCompte(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComptePro)) {
            return false;
        }
        return id != null && id.equals(((ComptePro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComptePro{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", nomAr='" + getNomAr() + "'" +
            ", nomFr='" + getNomFr() + "'" +
            ", prenomAr='" + getPrenomAr() + "'" +
            ", prenomFr='" + getPrenomFr() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", mail='" + getMail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", created_at='" + getCreatedAt() + "'" +
            ", update_at='" + getUpdatedAt() + "'" +
            ", deleted='" + isDeleted() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
