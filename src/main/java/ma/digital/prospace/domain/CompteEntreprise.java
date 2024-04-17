package ma.digital.prospace.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "compte_entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompteEntreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "compteEntreprises", allowSetters = true)
    private Entreprise entreprise;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "compteEntreprises", allowSetters = true)
    private ComptePro compte;

    @ElementCollection
    private Set<String> roles = new HashSet<>();

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public ComptePro getCompte() {
        return compte;
    }

    public void setCompte(ComptePro compte) {
        this.compte = compte;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompteEntreprise that = (CompteEntreprise) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CompteEntreprise{" +
                "id=" + id +
                ", entreprise=" + entreprise +
                ", compte=" + compte +
                ", roles=" + roles +
                '}';
    }
}
