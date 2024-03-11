package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.Data;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.springframework.stereotype.Component;


/**
 * A DTO for the {@link ma.digital.prospace.domain.Association} entity.
 */
@Component
@Data
public class AssociationDTO implements Serializable {

    private Long id;

    private String mail;

    private String telephone;

    private StatutAssociation statut;

    private EntrepriseDTO entreprise;

    private CompteProDTO compte;

    private RoleeDTO role;



}
