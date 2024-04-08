package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntrepriseDTO11 {
    @JsonProperty("NumRC")
    private String numRC;

    @JsonProperty("Denomination")
    private String denomination;

    @JsonProperty("FormeJuridique")
    private String formeJuridique;

    public String getNumRC() {
        return numRC;
    }

    public void setNumRC(String numRC) {
        this.numRC = numRC;
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
}
