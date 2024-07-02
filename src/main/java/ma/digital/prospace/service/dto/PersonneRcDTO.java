package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PersonneRcDTO {
    @JsonProperty("Identification")
    private IdentificationDTO identification;

    @JsonProperty("SuccurssalesOuAgences")
    private List<SuccursaleOuAgenceDTO> succurssalesOuAgences;

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



    public List<SuccursaleOuAgenceDTO> getSuccurssalesOuAgences() {
        return succurssalesOuAgences;
    }

    public void setSuccurssalesOuAgences(List<SuccursaleOuAgenceDTO> succurssalesOuAgences) {
        this.succurssalesOuAgences = succurssalesOuAgences;
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