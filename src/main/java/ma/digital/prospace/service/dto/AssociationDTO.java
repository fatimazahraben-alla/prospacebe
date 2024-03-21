package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.time.Instant;
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

    private String telephone;

    private String mail;

    private Instant dateFin;

    private Instant dateEffet;

    private StatutAssociation statut = StatutAssociation.PENDING;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public Instant getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }


    public StatutAssociation getStatut() {
        return statut;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssociationDTO)) return false;
        AssociationDTO that = (AssociationDTO) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssociationDTO{" +
                "id=" + id +
                ", compteID=" + compteID +
                ", entrepriseID=" + entrepriseID +
                ", roleID=" + roleID +
                ", telephone='" + telephone + '\'' +
                ", mail='" + mail + '\'' +
                ", dateFin=" + dateFin +
                ", dateEffet=" + dateEffet +
                ", statut=" + statut +
                '}';
    }
}
