package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link ma.digital.prospace.domain.CompteEntreprise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteEntrepriseDTO implements Serializable {

    private CompteProDTO comptePro;
    private EntrepriseDTO entreprise;
    private List<String> roles;
    private String transactionId;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }




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
