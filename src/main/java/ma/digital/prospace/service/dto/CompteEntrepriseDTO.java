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

    private CompteProDTO comptePro;
    private EntrepriseDTO entreprise;


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




    // prettier-ignore
    @Override
    public String toString() {
        return "CompteEntreprise{" +
                ", entreprise='" + getEntreprise() + "'" +
                ", compte='" + getComptePro() + "'" +
                ", roles='" + getRoles() + "'" +
                "}";
    }
}
