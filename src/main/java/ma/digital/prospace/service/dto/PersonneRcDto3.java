package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonneRcDto3 {
    @JsonProperty("NumRC")
    private String numRC;

    @JsonProperty("NumChronologique")
    private String numChronologique;

    @JsonProperty("Etablissement")
    private EtablissementDto etablissement;

    @JsonProperty("Commercant")
    private CommercantDto commercant;

    @JsonProperty("DateDeclaration")
    private String dateDeclaration;

    @JsonProperty("DateDebutActivite")
    private String dateDebutActivite;

    @JsonProperty("Observations")
    private ObservationDto3[] observations;

    @JsonProperty("Etat")
    private String etat;

    // Getters and setters


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

    public EtablissementDto getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(EtablissementDto etablissement) {
        this.etablissement = etablissement;
    }

    public CommercantDto getCommercant() {
        return commercant;
    }

    public void setCommercant(CommercantDto commercant) {
        this.commercant = commercant;
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

    public ObservationDto3[] getObservations() {
        return observations;
    }

    public void setObservations(ObservationDto3[] observations) {
        this.observations = observations;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}

