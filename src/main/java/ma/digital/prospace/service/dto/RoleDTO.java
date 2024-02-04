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
 * Role
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-04T18:38:04.509566+01:00[Africa/Casablanca]")
public class RoleDTO {

  private Long id;

  private String nom;

  private String description;

  private String fournisseurID;

  public RoleDTO id(Long id) {
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

  public RoleDTO nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
  */
  
  @Schema(name = "nom", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nom")
  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public RoleDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public RoleDTO fournisseurID(String fournisseurID) {
    this.fournisseurID = fournisseurID;
    return this;
  }

  /**
   * Get fournisseurID
   * @return fournisseurID
  */
  
  @Schema(name = "fournisseurID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fournisseurID")
  public String getFournisseurID() {
    return fournisseurID;
  }

  public void setFournisseurID(String fournisseurID) {
    this.fournisseurID = fournisseurID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoleDTO role = (RoleDTO) o;
    return Objects.equals(this.id, role.id) &&
        Objects.equals(this.nom, role.nom) &&
        Objects.equals(this.description, role.description) &&
        Objects.equals(this.fournisseurID, role.fournisseurID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, description, fournisseurID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Role {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    fournisseurID: ").append(toIndentedString(fournisseurID)).append("\n");
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

