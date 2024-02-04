package ma.digital.prospace.service.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Compte
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-04T18:38:04.509566+01:00[Africa/Casablanca]")
public class CompteDTO {

  private Long id;

  private String identifiant;

  private String typeIdentifiant;

  private String nomAr;

  private String nomFr;

  private String prenomAr;

  private String prenomFr;

  private String adress;

  private org.springframework.core.io.Resource photo;

  private String mail;

  private String telephone;

  private String statut;

  public CompteDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CompteDTO identifiant(String identifiant) {
    this.identifiant = identifiant;
    return this;
  }

  /**
   * Get identifiant
   * @return identifiant
  */
  
  @Schema(name = "identifiant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identifiant")
  public String getIdentifiant() {
    return identifiant;
  }

  public void setIdentifiant(String identifiant) {
    this.identifiant = identifiant;
  }

  public CompteDTO typeIdentifiant(String typeIdentifiant) {
    this.typeIdentifiant = typeIdentifiant;
    return this;
  }

  /**
   * Get typeIdentifiant
   * @return typeIdentifiant
  */
  
  @Schema(name = "typeIdentifiant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("typeIdentifiant")
  public String getTypeIdentifiant() {
    return typeIdentifiant;
  }

  public void setTypeIdentifiant(String typeIdentifiant) {
    this.typeIdentifiant = typeIdentifiant;
  }

  public CompteDTO nomAr(String nomAr) {
    this.nomAr = nomAr;
    return this;
  }

  /**
   * Get nomAr
   * @return nomAr
  */
  
  @Schema(name = "nomAr", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomAr")
  public String getNomAr() {
    return nomAr;
  }

  public void setNomAr(String nomAr) {
    this.nomAr = nomAr;
  }

  public CompteDTO nomFr(String nomFr) {
    this.nomFr = nomFr;
    return this;
  }

  /**
   * Get nomFr
   * @return nomFr
  */
  
  @Schema(name = "nomFr", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nomFr")
  public String getNomFr() {
    return nomFr;
  }

  public void setNomFr(String nomFr) {
    this.nomFr = nomFr;
  }

  public CompteDTO prenomAr(String prenomAr) {
    this.prenomAr = prenomAr;
    return this;
  }

  /**
   * Get prenomAr
   * @return prenomAr
  */
  
  @Schema(name = "prenomAr", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prenomAr")
  public String getPrenomAr() {
    return prenomAr;
  }

  public void setPrenomAr(String prenomAr) {
    this.prenomAr = prenomAr;
  }

  public CompteDTO prenomFr(String prenomFr) {
    this.prenomFr = prenomFr;
    return this;
  }

  /**
   * Get prenomFr
   * @return prenomFr
  */
  
  @Schema(name = "prenomFr", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prenomFr")
  public String getPrenomFr() {
    return prenomFr;
  }

  public void setPrenomFr(String prenomFr) {
    this.prenomFr = prenomFr;
  }

  public CompteDTO adress(String adress) {
    this.adress = adress;
    return this;
  }

  /**
   * Get adress
   * @return adress
  */
  
  @Schema(name = "adress", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("adress")
  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public CompteDTO photo(org.springframework.core.io.Resource photo) {
    this.photo = photo;
    return this;
  }

  /**
   * Get photo
   * @return photo
  */
  @Valid 
  @Schema(name = "photo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photo")
  public org.springframework.core.io.Resource getPhoto() {
    return photo;
  }

  public void setPhoto(org.springframework.core.io.Resource photo) {
    this.photo = photo;
  }

  public CompteDTO mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Get mail
   * @return mail
  */
  
  @Schema(name = "mail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mail")
  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public CompteDTO telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

  /**
   * Get telephone
   * @return telephone
  */
  
  @Schema(name = "telephone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("telephone")
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public CompteDTO statut(String statut) {
    this.statut = statut;
    return this;
  }

  /**
   * Get statut
   * @return statut
  */
  
  @Schema(name = "statut", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statut")
  public String getStatut() {
    return statut;
  }

  public void setStatut(String statut) {
    this.statut = statut;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompteDTO compte = (CompteDTO) o;
    return Objects.equals(this.id, compte.id) &&
        Objects.equals(this.identifiant, compte.identifiant) &&
        Objects.equals(this.typeIdentifiant, compte.typeIdentifiant) &&
        Objects.equals(this.nomAr, compte.nomAr) &&
        Objects.equals(this.nomFr, compte.nomFr) &&
        Objects.equals(this.prenomAr, compte.prenomAr) &&
        Objects.equals(this.prenomFr, compte.prenomFr) &&
        Objects.equals(this.adress, compte.adress) &&
        Objects.equals(this.photo, compte.photo) &&
        Objects.equals(this.mail, compte.mail) &&
        Objects.equals(this.telephone, compte.telephone) &&
        Objects.equals(this.statut, compte.statut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, identifiant, typeIdentifiant, nomAr, nomFr, prenomAr, prenomFr, adress, photo, mail, telephone, statut);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Compte {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    identifiant: ").append(toIndentedString(identifiant)).append("\n");
    sb.append("    typeIdentifiant: ").append(toIndentedString(typeIdentifiant)).append("\n");
    sb.append("    nomAr: ").append(toIndentedString(nomAr)).append("\n");
    sb.append("    nomFr: ").append(toIndentedString(nomFr)).append("\n");
    sb.append("    prenomAr: ").append(toIndentedString(prenomAr)).append("\n");
    sb.append("    prenomFr: ").append(toIndentedString(prenomFr)).append("\n");
    sb.append("    adress: ").append(toIndentedString(adress)).append("\n");
    sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    statut: ").append(toIndentedString(statut)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

