package ma.digital.prospace.service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 * * A DTO for the {@link ma.digital.prospace.domain.FournisseurService} entity.
 *  * * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
@Data
public class ResponseauthenticationDTO {
    @NotNull
    private Long compteID;
    @NotNull
    private Long fs;
    @NotNull
    private List<String> entreprises;



}
