package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntrepriseDTO implements Serializable {

    private UUID id;

    private String denomination;

    private String statutJuridique;
    private String tribunal;

    private String numeroRC;

    private String ice;

    private String activite;

    private String formeJuridique;

    private String dateImmatriculation;
    private String etat;
    private String capital;

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getStatutJuridique() {
        return statutJuridique;
    }

    public void setStatutJuridique(String statutJuridique) {
        this.statutJuridique = statutJuridique;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getNumeroRC() {
        return numeroRC;
    }

    public void setNumeroRC(String numeroRC) {
        this.numeroRC = numeroRC;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public String getDateImmatriculation() {
        return dateImmatriculation;
    }

    public void setDateImmatriculation(String dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntrepriseDTO)) {
            return false;
        }

        EntrepriseDTO entreprise = (EntrepriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entreprise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

}