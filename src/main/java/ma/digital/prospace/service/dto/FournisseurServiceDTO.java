package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.Rolee;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link ma.digital.prospace.domain.FournisseurService} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FournisseurServiceDTO	 implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Set<Rolee> roles = new HashSet<>();

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
    public Set<Rolee> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rolee> roles) {
        this.roles = roles;
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

