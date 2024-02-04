package ma.digital.prospace.service.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Procuration
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-04T18:38:04.509566+01:00[Africa/Casablanca]")
public class ProcurationDTO {

  private Integer id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateEffet;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateFin;

  private String utilisateurPro;

  private String gestionnaireEspacePro;

  public ProcurationDTO id(Integer id) {
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

  public ProcurationDTO dateEffet(LocalDate dateEffet) {
    this.dateEffet = dateEffet;
    return this;
  }

  /**
   * Get dateEffet
   * @return dateEffet
  */
  @Valid 
  @Schema(name = "dateEffet", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateEffet")
  public LocalDate getDateEffet() {
    return dateEffet;
  }

  public void setDateEffet(LocalDate dateEffet) {
    this.dateEffet = dateEffet;
  }

  public ProcurationDTO dateFin(LocalDate dateFin) {
    this.dateFin = dateFin;
    return this;
  }

  /**
   * Get dateFin
   * @return dateFin
  */
  @Valid 
  @Schema(name = "dateFin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateFin")
  public LocalDate getDateFin() {
    return dateFin;
  }

  public void setDateFin(LocalDate dateFin) {
    this.dateFin = dateFin;
  }

  public ProcurationDTO utilisateurPro(String utilisateurPro) {
    this.utilisateurPro = utilisateurPro;
    return this;
  }

  /**
   * Get utilisateurPro
   * @return utilisateurPro
  */
  
  @Schema(name = "utilisateurPro", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("utilisateurPro")
  public String getUtilisateurPro() {
    return utilisateurPro;
  }

  public void setUtilisateurPro(String utilisateurPro) {
    this.utilisateurPro = utilisateurPro;
  }

  public ProcurationDTO gestionnaireEspacePro(String gestionnaireEspacePro) {
    this.gestionnaireEspacePro = gestionnaireEspacePro;
    return this;
  }

  /**
   * Get gestionnaireEspacePro
   * @return gestionnaireEspacePro
  */
  
  @Schema(name = "gestionnaireEspacePro", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gestionnaireEspacePro")
  public String getGestionnaireEspacePro() {
    return gestionnaireEspacePro;
  }

  public void setGestionnaireEspacePro(String gestionnaireEspacePro) {
    this.gestionnaireEspacePro = gestionnaireEspacePro;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProcurationDTO procuration = (ProcurationDTO) o;
    return Objects.equals(this.id, procuration.id) &&
        Objects.equals(this.dateEffet, procuration.dateEffet) &&
        Objects.equals(this.dateFin, procuration.dateFin) &&
        Objects.equals(this.utilisateurPro, procuration.utilisateurPro) &&
        Objects.equals(this.gestionnaireEspacePro, procuration.gestionnaireEspacePro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dateEffet, dateFin, utilisateurPro, gestionnaireEspacePro);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Procuration {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dateEffet: ").append(toIndentedString(dateEffet)).append("\n");
    sb.append("    dateFin: ").append(toIndentedString(dateFin)).append("\n");
    sb.append("    utilisateurPro: ").append(toIndentedString(utilisateurPro)).append("\n");
    sb.append("    gestionnaireEspacePro: ").append(toIndentedString(gestionnaireEspacePro)).append("\n");
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

