package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObservationDto3 {
    @JsonProperty("DeteObservation")
    private String deteObservation;

    @JsonProperty("Resume")
    private String resume;

    public String getDeteObservation() {
        return deteObservation;
    }

    public void setDeteObservation(String deteObservation) {
        this.deteObservation = deteObservation;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
