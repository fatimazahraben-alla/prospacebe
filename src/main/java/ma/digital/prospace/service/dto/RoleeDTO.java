package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class RoleeDTO implements Serializable {

    private UUID id;

    private String nom;

    private String description;

    private String fournisseurServiceId;

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getFournisseurServiceId() {
        return fournisseurServiceId;
    }

    public void setFournisseurServiceId(String fournisseurServiceId) {
        this.fournisseurServiceId = fournisseurServiceId;
    }

    // Equals, hashCode, and toString methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleeDTO roleeDTO = (RoleeDTO) o;
        return Objects.equals(id, roleeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RoleeDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", fournisseurServiceId=" + fournisseurServiceId +
                '}';
    }
}
