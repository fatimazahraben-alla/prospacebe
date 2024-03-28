package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentificationDTO2 {
    @JsonProperty("NumRC")
    private String numRC;

    @JsonProperty("SiegeSocial")
    private String siegeSocial;

    @JsonProperty("Denomination")
    private String denomination;

    @JsonProperty("FormeJuridique")
    private String formeJuridique;

    @JsonProperty("Etat")
    private String etat;

    public String getNumRC() {
        return numRC;
    }

    public void setNumRC(String numRC) {
        this.numRC = numRC;
    }

    public String getSiegeSocial() {
        return siegeSocial;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
