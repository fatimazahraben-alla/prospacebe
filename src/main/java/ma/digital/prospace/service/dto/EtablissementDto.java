package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EtablissementDto {
    @JsonProperty("Adresse")
    private String adresse;

    @JsonProperty("Ville")
    private String ville;

    @JsonProperty("Activite")
    private String activite;

    @JsonProperty("Enseigne")
    private String enseigne;

    @JsonProperty("OrigineFond")
    private String origineFond;

    // Getters and setters

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public String getOrigineFond() {
        return origineFond;
    }

    public void setOrigineFond(String origineFond) {
        this.origineFond = origineFond;
    }
}
