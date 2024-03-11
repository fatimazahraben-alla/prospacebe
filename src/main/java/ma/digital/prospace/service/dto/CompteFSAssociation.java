package ma.digital.prospace.service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 * * A DTO for the {@link ma.digital.prospace.domain.FournisseurService} entity.
 *  * * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
@Data
public class CompteFSAssociation {
    @NotNull
    private Long compteID;
    @NotNull
    private Long fs;
    @NotNull
    private List<String> entreprises;

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

    public List<String> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<String> entreprises) {
        this.entreprises = entreprises;
    }
}
