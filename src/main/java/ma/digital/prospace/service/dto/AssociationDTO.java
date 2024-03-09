package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import ma.digital.prospace.domain.enumeration.StatutAssociation;
import org.springframework.stereotype.Component;


/**
 * A DTO for the {@link ma.digital.prospace.domain.Association} entity.
 */
@Component
public class AssociationDTO implements Serializable {

    private Long id;

    @NotNull
    private Long compteID;

    @NotNull
    private Long entrepriseID;

    @NotNull
    private Long roleID;

    private StatutAssociation statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompteID() {
        return compteID;
    }

    public void setCompteID(Long compteID) {
        this.compteID = compteID;
    }

    public Long getEntrepriseID() {
        return entrepriseID;
    }

    public void setEntrepriseID(Long entrepriseID) {
        this.entrepriseID = entrepriseID;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public StatutAssociation getStatut() {
        return statut;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociationDTO)) {
            return false;
        }

        AssociationDTO association = (AssociationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, association.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssociationDTO{" +
                "id=" + getId() +
                ", compteID='" + getCompteID() + "'" +
                ", entrepriseID='" + getEntrepriseID() + "'" +
                ", roleID='" + getRoleID() + "'" +
                ", statut='" + getStatut() + "'" +
                "}";
    }
}
