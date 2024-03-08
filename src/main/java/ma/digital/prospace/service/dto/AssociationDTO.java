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


    private Long COMPTEID;


    private Long ENTREPRISEID;


    private Long ROLEID;

    private StatutAssociation statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCOMPTEID() {
        return COMPTEID;
    }

    public void setCOMPTEID(Long COMPTEID) {
        this.COMPTEID = COMPTEID;
    }

    public Long getENTREPRISEID() {
        return ENTREPRISEID;
    }

    public void setENTREPRISEID(Long ENTREPRISEID) {
        this.ENTREPRISEID = ENTREPRISEID;
    }

    public Long getROLEID() {
        return ROLEID;
    }

    public void setROLEID(Long ROLEID) {
        this.ROLEID = ROLEID;
    }

    public StatutAssociation getStatut() {
        return statut;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }
}
