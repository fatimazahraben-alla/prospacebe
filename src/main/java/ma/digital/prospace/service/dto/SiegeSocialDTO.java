package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiegeSocialDTO {
    @JsonProperty("Adresse")
    private String adresse;

    @JsonProperty("Ville")
    private String ville;

    @JsonProperty("Activite")
    private String activite;

    @JsonProperty("Enseigne")
    private String enseigne;

    @JsonProperty("TypeFond")
    private String typeFond;

    @JsonProperty("OrigineFond")
    private String origineFond;

    @JsonProperty("Domiciliation")
    private DomiciliationDTO domiciliation;

    @JsonProperty("FondeDePouvoir")
    private FondeDePouvoirDTO fondeDePouvoir;

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

    public String getTypeFond() {
        return typeFond;
    }

    public void setTypeFond(String typeFond) {
        this.typeFond = typeFond;
    }

    public String getOrigineFond() {
        return origineFond;
    }

    public void setOrigineFond(String origineFond) {
        this.origineFond = origineFond;
    }

    public DomiciliationDTO getDomiciliation() {
        return domiciliation;
    }

    public void setDomiciliation(DomiciliationDTO domiciliation) {
        this.domiciliation = domiciliation;
    }

    public FondeDePouvoirDTO getFondeDePouvoir() {
        return fondeDePouvoir;
    }

    public void setFondeDePouvoir(FondeDePouvoirDTO fondeDePouvoir) {
        this.fondeDePouvoir = fondeDePouvoir;
    }
}
