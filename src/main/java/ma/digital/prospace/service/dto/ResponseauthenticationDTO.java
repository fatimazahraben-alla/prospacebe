package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 * * A DTO for the {@link ma.digital.prospace.domain.FournisseurService} entity.
 *  * * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
public class ResponseauthenticationDTO {
    @NotNull
    private Long compteID;
    @NotNull
    private Long fs;
    @NotNull
    private List<String> entreprise;

    public Long getCompteID() {
        return compteID;
    }

    public void setCompteID(Long compteID) {
        this.compteID = compteID;
    }

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    public List<String> getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(List<String> entreprise) {
        this.entreprise = entreprise;
    }
}
