package ma.digital.prospace.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ma.digital.prospace.domain.enumeration.StatutCompte;
import org.hibernate.annotations.GenericGenerator;

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
    @Size(max = 50)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Size(max = 100)
    @Column(name = "identifiant", nullable = true)
    private String identifiant;

    @Size(max = 60)
    @Enumerated(EnumType.STRING)
    @Column(name = "typeidentifiant", length = 10)
    private typeidentifiant typeidentifiant;

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
    @Column(name = "photo", nullable = true)
    private String photo;

    @Transient
    @Column(name = "mail")
    private String mail;
    @Transient
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "created_at")
    private Date createdAt;


    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutCompte statut;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "compte_entreprise",
            joinColumns = @JoinColumn(name = "compte_id"),
            inverseJoinColumns = @JoinColumn(name = "entreprise_geree_id")
    )
    @JsonIgnoreProperties(value = { "gerants", "password" }, allowSetters = true)
    private Set<Entreprise> entrepriseGeree = new HashSet<>();


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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public ma.digital.prospace.domain.enumeration.typeidentifiant getTypeidentifiant() {
        return typeidentifiant;
    }

    public void setTypeidentifiant(ma.digital.prospace.domain.enumeration.typeidentifiant typeidentifiant) {
        this.typeidentifiant = typeidentifiant;
    }

    public String getNomAr() {
        return nomAr;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getNomFr() {
        return nomFr;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getPrenomAr() {
        return prenomAr;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getPrenomFr() {
        return prenomFr;
    }

    public void setPrenomFr(String prenomFr) {
        this.prenomFr = prenomFr;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
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

    public Set<Entreprise> getEntrepriseGeree() {
        return entrepriseGeree;
    }

    public void setEntrepriseGeree(Set<Entreprise> entrepriseGeree) {
        this.entrepriseGeree = entrepriseGeree;
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


}