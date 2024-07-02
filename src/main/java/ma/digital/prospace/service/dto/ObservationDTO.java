package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ObservationDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("DeteObservation")
    private Date dateObservation;

    @JsonProperty("Resume")
    private String resume;

    public Date getDateObservation() {
        return dateObservation;
    }

    public void setDateObservation(Date dateObservation) {
        this.dateObservation = dateObservation;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
