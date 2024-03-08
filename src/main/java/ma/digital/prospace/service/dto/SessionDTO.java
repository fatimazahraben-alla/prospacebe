package ma.digital.prospace.service.dto;

import lombok.Data;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.Rolee;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
public class SessionDTO {
    private String transactionId;
    private Date createdAt;

    public ComptePro getCompte() {
        return compte;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    private List<String> roles;

    public void setCompte(ComptePro compte) {
        this.compte = compte;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }



    private ComptePro compte;
    private Entreprise entreprise;


    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    private Long compteId;
    private Long entrepriseId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    private String jsonData;



}
