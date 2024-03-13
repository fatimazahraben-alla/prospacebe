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
    private CompteProDTO comptePro;
    private EntrepriseDTO entreprise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteProDTO getComptePro() {
        return comptePro;
    }

    public void setComptePro(CompteProDTO comptePro) {
        this.comptePro = comptePro;
    }

    public EntrepriseDTO getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO entreprise) {
        this.entreprise = entreprise;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }



    private String transactionId;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    private List<String> roles;

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
                ", compte='" + getComptePro() + "'" +
                ", roles='" + getRoles() + "'" +
                "}";
    }
}
