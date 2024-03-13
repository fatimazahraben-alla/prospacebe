package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class RoleeDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Long fournisseurServiceId;

    // Getters and setters

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

    public Long getFournisseurServiceId() {
        return fournisseurServiceId;
    }

    public void setFournisseurServiceId(Long fournisseurServiceId) {
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
