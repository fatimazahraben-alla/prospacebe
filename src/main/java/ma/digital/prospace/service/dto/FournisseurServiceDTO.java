package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ma.digital.prospace.domain.FournisseurServiceDTO} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FournisseurServiceDTO	 implements Serializable {

    private Long id;

    private String nom;

    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournisseurServiceDTO)) {
            return false;
        }

        FournisseurServiceDTO fournisseurService = (FournisseurServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fournisseurService.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournisseurServiceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

