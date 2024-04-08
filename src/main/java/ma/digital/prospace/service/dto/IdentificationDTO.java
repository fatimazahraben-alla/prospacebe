package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IdentificationDTO {
    @JsonProperty("NumRC")
    private String numRC;

    @JsonProperty("NumChronologique")
    private String numChronologique;


    @JsonProperty("Denomination")
    private String denomination;

    @JsonProperty("FormeJuridique")
    private String formeJuridique;

    @JsonProperty("Sigle")
    private String sigle;

    @JsonProperty("Capital")
    private Double capital;

    @JsonProperty("Activite")
    private String activite;

    @JsonProperty("DateDeclaration")
    private String dateDeclaration;

    @JsonProperty("DateDebutActivite")
    private String dateDebutActivite;

    @JsonProperty("ICE")
    private String ice;

    @JsonProperty("Etat")
    private String etat;

    @JsonProperty("Enseigne")
    private String enseigne;

    @JsonProperty("Observations")
    private List<ObservationDTO> observations;

    public List<ObservationDTO> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationDTO> observations) {
        this.observations = observations;
    }

    public String getNumRC() {
        return numRC;
    }

    public void setNumRC(String numRC) {
        this.numRC = numRC;
    }

    public String getNumChronologique() {
        return numChronologique;
    }

    public void setNumChronologique(String numChronologique) {
        this.numChronologique = numChronologique;
    }


    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(String dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public String getDateDebutActivite() {
        return dateDebutActivite;
    }

    public void setDateDebutActivite(String dateDebutActivite) {
        this.dateDebutActivite = dateDebutActivite;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }
}
