package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EntrepriseWSMJ {
    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("PersonneRc")
    private PersonneRcDTO personneRc;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonneRcDTO getPersonneRc() {
        return personneRc;
    }

    public void setPersonneRc(PersonneRcDTO personneRc) {
        this.personneRc = personneRc;
    }
}


