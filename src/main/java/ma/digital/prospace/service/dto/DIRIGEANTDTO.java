package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DIRIGEANTDTO {
    @JsonProperty("Success")
    private boolean success;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("PersonneRc")
    private PersonneRcDTO2 personneRc;

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

    public PersonneRcDTO2 getPersonneRc() {
        return personneRc;
    }

    public void setPersonneRc(PersonneRcDTO2 personneRc) {
        this.personneRc = personneRc;
    }
}
