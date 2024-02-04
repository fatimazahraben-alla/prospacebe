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
 * Association
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-02-04T18:38:04.509566+01:00[Africa/Casablanca]")
public class AssociationDTO {

  private Integer id;

  private String compteID;

  private String entrepriseID;

  private String roleID;

  private String statut;

  public AssociationDTO id(Integer id) {
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

  public AssociationDTO compteID(String compteID) {
    this.compteID = compteID;
    return this;
  }

  /**
   * Get compteID
   * @return compteID
  */
  
  @Schema(name = "compteID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("compteID")
  public String getCompteID() {
    return compteID;
  }

  public void setCompteID(String compteID) {
    this.compteID = compteID;
  }

  public AssociationDTO entrepriseID(String entrepriseID) {
    this.entrepriseID = entrepriseID;
    return this;
  }

  /**
   * Get entrepriseID
   * @return entrepriseID
  */
  
  @Schema(name = "entrepriseID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("entrepriseID")
  public String getEntrepriseID() {
    return entrepriseID;
  }

  public void setEntrepriseID(String entrepriseID) {
    this.entrepriseID = entrepriseID;
  }

  public AssociationDTO roleID(String roleID) {
    this.roleID = roleID;
    return this;
  }

  /**
   * Get roleID
   * @return roleID
  */
  
  @Schema(name = "roleID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("roleID")
  public String getRoleID() {
    return roleID;
  }

  public void setRoleID(String roleID) {
    this.roleID = roleID;
  }

  public AssociationDTO statut(String statut) {
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
    AssociationDTO association = (AssociationDTO) o;
    return Objects.equals(this.id, association.id) &&
        Objects.equals(this.compteID, association.compteID) &&
        Objects.equals(this.entrepriseID, association.entrepriseID) &&
        Objects.equals(this.roleID, association.roleID) &&
        Objects.equals(this.statut, association.statut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, compteID, entrepriseID, roleID, statut);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Association {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    compteID: ").append(toIndentedString(compteID)).append("\n");
    sb.append("    entrepriseID: ").append(toIndentedString(entrepriseID)).append("\n");
    sb.append("    roleID: ").append(toIndentedString(roleID)).append("\n");
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

