package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the {@link ma.digital.prospace.domain.Rolee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RoleeDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Long fournisseurID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFournisseurID() {
        return fournisseurID;
    }

    public void setFournisseurID(Long fournisseurID) {
        this.fournisseurID = fournisseurID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleeDTO)) {
            return false;
        }

        RoleeDTO rolee = (RoleeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rolee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rolee{" +
                "id=" + getId() +
                ", nom='" + getNom() + "'" +
                ", description='" + getDescription() + "'" +
                ", fournisseurID='" + getFournisseurID() + "'" +
                "}";
    }
}
