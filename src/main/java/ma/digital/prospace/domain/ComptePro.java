package ma.digital.prospace.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ma.digital.prospace.domain.enumeration.StatutCompte;

/**
 * A ComptePro.
 */
@Entity
@Data
@Table(name = "compte_pro")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComptePro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   @Transient
    @NotNull
    @Size(max = 10)
    @Column(name = "identifiant", length = 10, nullable = false)
    private String identifiant;

    @Transient
    @NotNull
    @Size(max = 25)
    @Column(name = "type_identifiant", length = 25, nullable = false)
    private String typeIdentifiant;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "nom_ar", length = 50, nullable = false)
    private String nomAr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "nom_fr", length = 50, nullable = false)
    private String nomFr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "prenom_ar", length = 50, nullable = false)
    private String prenomAr;

    @Transient
    @NotNull
    @Size(max = 50)
    @Column(name = "prenom_fr", length = 50, nullable = false)
    private String prenomFr;

    @Transient
    @Size(max = 50)
    @Column(name = "adresse", length = 50)
    private String adresse;

   @Transient
    @Lob
    @Column(name = "photo", nullable = false)
    private String photo;

    @Transient
    @NotNull
    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;
    @Transient
    @Column(name = "mail")
    private String mail;

    @Transient
    @Column(name = "telephone")
    private String telephone;

    @Transient
    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutCompte statut;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @Column(name = "deleted")
    private  Boolean deleted;

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


 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getIdentifiant() {
  return identifiant;
 }

 public void setIdentifiant(String identifiant) {
  this.identifiant = identifiant;
 }

 public String getTypeIdentifiant() {
  return typeIdentifiant;
 }

 public void setTypeIdentifiant(String typeIdentifiant) {
  this.typeIdentifiant = typeIdentifiant;
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

 public String getPhotoContentType() {
  return photoContentType;
 }

 public void setPhotoContentType(String photoContentType) {
  this.photoContentType = photoContentType;
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

 public StatutCompte getStatut() {
  return statut;
 }

 public void setStatut(StatutCompte statut) {
  this.statut = statut;
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

 public Boolean getDeleted() {
  return deleted;
 }

 public void setDeleted(Boolean deleted) {
  this.deleted = deleted;
 }

 public Entreprise getEntrepriseGeree() {
  return entrepriseGeree;
 }

 public void setEntrepriseGeree(Entreprise entrepriseGeree) {
  this.entrepriseGeree = entrepriseGeree;
 }

 public Set<Procuration> getMandataires() {
  return mandataires;
 }

 public void setMandataires(Set<Procuration> mandataires) {
  this.mandataires = mandataires;
 }

 public Set<Procuration> getMandants() {
  return mandants;
 }

 public void setMandants(Set<Procuration> mandants) {
  this.mandants = mandants;
 }

 public Set<Association> getAssociations() {
  return associations;
 }

 public void setAssociations(Set<Association> associations) {
  this.associations = associations;
 }

}
