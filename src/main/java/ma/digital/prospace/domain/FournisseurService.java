package ma.digital.prospace.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.Size;

/**
 * A FournisseurService.
 */
@Entity
@Table(name = "fournisseur_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FournisseurService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 50)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "fs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fs" }, allowSetters = true)
    private Set<Rolee> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FournisseurService id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public FournisseurService nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public FournisseurService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Rolee> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Rolee> rolees) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.setFs(null));
        }
        if (rolees != null) {
            rolees.forEach(i -> i.setFs(this));
        }
        this.roles = rolees;
    }

    public FournisseurService roles(Set<Rolee> rolees) {
        this.setRoles(rolees);
        return this;
    }

    public FournisseurService addRoles(Rolee rolee) {
        this.roles.add(rolee);
        rolee.setFs(this);
        return this;
    }

    public FournisseurService removeRoles(Rolee rolee) {
        this.roles.remove(rolee);
        rolee.setFs(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FournisseurService)) {
            return false;
        }
        return id != null && id.equals(((FournisseurService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FournisseurService{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
