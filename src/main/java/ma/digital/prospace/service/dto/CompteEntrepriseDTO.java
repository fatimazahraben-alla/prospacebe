package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link ma.digital.prospace.domain.CompteEntreprise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteEntrepriseDTO implements Serializable {

    private Long id;

    @NotNull
    private Entreprise entreprise;

    @NotNull
    private ComptePro compte;

    public Long getCompteID() {
        return compteID;
    }

    public void setCompteID(Long compteID) {
        this.compteID = compteID;
    }

    private Long compteID;

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    private Long fs;



    @Size(max = 50)
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteEntrepriseDTO)) {
            return false;
        }

        CompteEntrepriseDTO compteEntrepriseDTO = (CompteEntrepriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compteEntrepriseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteEntreprise{" +
                "id=" + getId() +
                ", entreprise='" + getEntreprise() + "'" +
                ", compte='" + getCompte() + "'" +
                ", roles='" + getRoles() + "'" +
                "}";
    }
}
