package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersonneRcDTO2 {
    @JsonProperty("Identification")
    private IdentificationDTO2 identification;

    @JsonProperty("DirigeantsPM")
    private List<DirigeantPMDTO2> dirigeantsPM;

    @JsonProperty("DirigeantsPN")
    private List<DirigeantPNDTO2> dirigeantsPN;

    public IdentificationDTO2 getIdentification() {
        return identification;
    }

    public void setIdentification(IdentificationDTO2 identification) {
        this.identification = identification;
    }

    public List<DirigeantPMDTO2> getDirigeantsPM() {
        return dirigeantsPM;
    }

    public void setDirigeantsPM(List<DirigeantPMDTO2> dirigeantsPM) {
        this.dirigeantsPM = dirigeantsPM;
    }

    public List<DirigeantPNDTO2> getDirigeantsPN() {
        return dirigeantsPN;
    }

    public void setDirigeantsPN(List<DirigeantPNDTO2> dirigeantsPN) {
        this.dirigeantsPN = dirigeantsPN;
    }
}
