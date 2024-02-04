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
 * Entreprise
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-04T18:38:04.509566+01:00[Africa/Casablanca]")
public class EntrepriseDTO {

  private Integer id;

  private String denomination;

  private String statutJuridique;

  private String tribunal;

  private String numeroRC;

  private String ice;

  private String activite;

  private String formeJuridique;

  private String dateImmatriculation;

  private String etat;

  private String gerant;

  public EntrepriseDTO id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EntrepriseDTO denomination(String denomination) {
    this.denomination = denomination;
    return this;
  }

  /**
   * Get denomination
   * @return denomination
  */
  
  @Schema(name = "denomination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("denomination")
  public String getDenomination() {
    return denomination;
  }

  public void setDenomination(String denomination) {
    this.denomination = denomination;
  }

  public EntrepriseDTO statutJuridique(String statutJuridique) {
    this.statutJuridique = statutJuridique;
    return this;
  }

  /**
   * Get statutJuridique
   * @return statutJuridique
  */
  
  @Schema(name = "statutJuridique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statutJuridique")
  public String getStatutJuridique() {
    return statutJuridique;
  }

  public void setStatutJuridique(String statutJuridique) {
    this.statutJuridique = statutJuridique;
  }

  public EntrepriseDTO tribunal(String tribunal) {
    this.tribunal = tribunal;
    return this;
  }

  /**
   * Get tribunal
   * @return tribunal
  */
  
  @Schema(name = "tribunal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tribunal")
  public String getTribunal() {
    return tribunal;
  }

  public void setTribunal(String tribunal) {
    this.tribunal = tribunal;
  }

  public EntrepriseDTO numeroRC(String numeroRC) {
    this.numeroRC = numeroRC;
    return this;
  }

  /**
   * Get numeroRC
   * @return numeroRC
  */
  
  @Schema(name = "numeroRC", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("numeroRC")
  public String getNumeroRC() {
    return numeroRC;
  }

  public void setNumeroRC(String numeroRC) {
    this.numeroRC = numeroRC;
  }

  public EntrepriseDTO ice(String ice) {
    this.ice = ice;
    return this;
  }

  /**
   * Get ice
   * @return ice
  */
  
  @Schema(name = "ice", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ice")
  public String getIce() {
    return ice;
  }

  public void setIce(String ice) {
    this.ice = ice;
  }

  public EntrepriseDTO activite(String activite) {
    this.activite = activite;
    return this;
  }

  /**
   * Get activite
   * @return activite
  */
  
  @Schema(name = "activite", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("activite")
  public String getActivite() {
    return activite;
  }

  public void setActivite(String activite) {
    this.activite = activite;
  }

  public EntrepriseDTO formeJuridique(String formeJuridique) {
    this.formeJuridique = formeJuridique;
    return this;
  }

  /**
   * Get formeJuridique
   * @return formeJuridique
  */
  
  @Schema(name = "formeJuridique", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("formeJuridique")
  public String getFormeJuridique() {
    return formeJuridique;
  }

  public void setFormeJuridique(String formeJuridique) {
    this.formeJuridique = formeJuridique;
  }

  public EntrepriseDTO dateImmatriculation(String dateImmatriculation) {
    this.dateImmatriculation = dateImmatriculation;
    return this;
  }

  /**
   * Get dateImmatriculation
   * @return dateImmatriculation
  */
  
  @Schema(name = "dateImmatriculation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateImmatriculation")
  public String getDateImmatriculation() {
    return dateImmatriculation;
  }

  public void setDateImmatriculation(String dateImmatriculation) {
    this.dateImmatriculation = dateImmatriculation;
  }

  public EntrepriseDTO etat(String etat) {
    this.etat = etat;
    return this;
  }

  /**
   * Get etat
   * @return etat
  */
  
  @Schema(name = "etat", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("etat")
  public String getEtat() {
    return etat;
  }

  public void setEtat(String etat) {
    this.etat = etat;
  }

  public EntrepriseDTO gerant(String gerant) {
    this.gerant = gerant;
    return this;
  }

  /**
   * Get gerant
   * @return gerant
  */
  
  @Schema(name = "gerant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gerant")
  public String getGerant() {
    return gerant;
  }

  public void setGerant(String gerant) {
    this.gerant = gerant;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntrepriseDTO entreprise = (EntrepriseDTO) o;
    return Objects.equals(this.id, entreprise.id) &&
        Objects.equals(this.denomination, entreprise.denomination) &&
        Objects.equals(this.statutJuridique, entreprise.statutJuridique) &&
        Objects.equals(this.tribunal, entreprise.tribunal) &&
        Objects.equals(this.numeroRC, entreprise.numeroRC) &&
        Objects.equals(this.ice, entreprise.ice) &&
        Objects.equals(this.activite, entreprise.activite) &&
        Objects.equals(this.formeJuridique, entreprise.formeJuridique) &&
        Objects.equals(this.dateImmatriculation, entreprise.dateImmatriculation) &&
        Objects.equals(this.etat, entreprise.etat) &&
        Objects.equals(this.gerant, entreprise.gerant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, denomination, statutJuridique, tribunal, numeroRC, ice, activite, formeJuridique, dateImmatriculation, etat, gerant);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Entreprise {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    denomination: ").append(toIndentedString(denomination)).append("\n");
    sb.append("    statutJuridique: ").append(toIndentedString(statutJuridique)).append("\n");
    sb.append("    tribunal: ").append(toIndentedString(tribunal)).append("\n");
    sb.append("    numeroRC: ").append(toIndentedString(numeroRC)).append("\n");
    sb.append("    ice: ").append(toIndentedString(ice)).append("\n");
    sb.append("    activite: ").append(toIndentedString(activite)).append("\n");
    sb.append("    formeJuridique: ").append(toIndentedString(formeJuridique)).append("\n");
    sb.append("    dateImmatriculation: ").append(toIndentedString(dateImmatriculation)).append("\n");
    sb.append("    etat: ").append(toIndentedString(etat)).append("\n");
    sb.append("    gerant: ").append(toIndentedString(gerant)).append("\n");
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

