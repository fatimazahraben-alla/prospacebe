package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import ma.digital.prospace.domain.enumeration.StatutAssociation;


/**
 * A DTO for the {@link ma.digital.prospace.domain.AssociationDTO} entity.
 */
public class AssociationDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dateEffet;

    private Instant dateFin;

    private String mail;

    private String telephone;

    private StatutAssociation statut;

    private EntrepriseDTO entreprise;

    private CompteProDTO compte;

    private RoleeDTO role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutAssociation getStatut() {
        return statut;
    }

    public void setStatut(StatutAssociation statut) {
        this.statut = statut;
    }

    public EntrepriseDTO getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO entreprise) {
        this.entreprise = entreprise;
    }

    public CompteProDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteProDTO compte) {
        this.compte = compte;
    }

    public RoleeDTO getRole() {
        return role;
    }

    public void setRole(RoleeDTO role) {
        this.role = role;
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
            ", dateEffet='" + getDateEffet() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", mail='" + getMail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", statut='" + getStatut() + "'" +
            ", entreprise=" + getEntreprise() +
            ", compte=" + getCompte() +
            ", role=" + getRole() +
            "}";
    }
}

