package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersonneRc {
    @JsonProperty("Identification")
    private IdentificationDTO identification;

    @JsonProperty("SiegeSocial")
    private SiegeSocialDTO siegeSocial;

    @JsonProperty("SuccursalesOuAgences")
    private List<SuccursaleOuAgenceDTO> succursalesOuAgences;

    @JsonProperty("DirigeantsPM")
    private List<DirigeantPMDTO> dirigeantsPM;

    @JsonProperty("DirigeantsPN")
    private List<DirigeantPNDTO> dirigeantsPN;

    public IdentificationDTO getIdentification() {
        return identification;
    }

    public void setIdentification(IdentificationDTO identification) {
        this.identification = identification;
    }

    public SiegeSocialDTO getSiegeSocial() {
        return siegeSocial;
    }

    public void setSiegeSocial(SiegeSocialDTO siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public List<SuccursaleOuAgenceDTO> getSuccursalesOuAgences() {
        return succursalesOuAgences;
    }

    public void setSuccursalesOuAgences(List<SuccursaleOuAgenceDTO> succursalesOuAgences) {
        this.succursalesOuAgences = succursalesOuAgences;
    }

    public List<DirigeantPMDTO> getDirigeantsPM() {
        return dirigeantsPM;
    }

    public void setDirigeantsPM(List<DirigeantPMDTO> dirigeantsPM) {
        this.dirigeantsPM = dirigeantsPM;
    }

    public List<DirigeantPNDTO> getDirigeantsPN() {
        return dirigeantsPN;
    }

    public void setDirigeantsPN(List<DirigeantPNDTO> dirigeantsPN) {
        this.dirigeantsPN = dirigeantsPN;
    }
}
