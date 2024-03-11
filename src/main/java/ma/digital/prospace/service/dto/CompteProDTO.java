package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Id;
import lombok.*;
import ma.digital.prospace.domain.enumeration.StatutCompte;
import org.springframework.stereotype.Component;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 */

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteProDTO implements Serializable {


    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private  Boolean deleted;








    // prettier-ignore

}

