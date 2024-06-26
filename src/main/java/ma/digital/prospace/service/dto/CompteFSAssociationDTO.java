package ma.digital.prospace.service.dto;

import lombok.Data;
import ma.digital.prospace.domain.Session;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 * * A DTO for the {@link ma.digital.prospace.domain.FournisseurService} entity.
 *  * * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */

@Data
@Component
public class CompteFSAssociationDTO {
    @NotNull
    private String compteID;
    @NotNull
    private String fs;
    @NotNull
    private List<String> entreprises;

    public CompteProDTO getComptePro() {
        return comptePro;
    }

    public void setComptePro(CompteProDTO comptePro) {
        this.comptePro = comptePro;
    }

    public EntrepriseDTO2 getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO2 entreprise) {
        this.entreprise = entreprise;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    private CompteProDTO comptePro;
    private EntrepriseDTO2 entreprise;
    private List<String> roles;
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    private String statut = Session.Status.IN_PROGRESS.toString();

    public String getCompteID() {
        return compteID;
    }

    public void setCompteID(String compteID) {
        this.compteID = compteID;
    }

    public String getFs() {
        return fs;
    }

    public void setFs(String fs) {
        this.fs = fs;
    }

    public List<String> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<String> entreprises) {
        this.entreprises = entreprises;
    }
}