package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class InvitationDTO {
    private Long id;
    private String token;
    private String typeIdentifiantTo;
    private String identifiantTo;
    private String object;
    private Date dateCreation;
    private Date dateFin;
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTypeIdentifiantTo() {
        return typeIdentifiantTo;
    }

    public void setTypeIdentifiantTo(String typeIdentifiantTo) {
        this.typeIdentifiantTo = typeIdentifiantTo;
    }

    public String getIdentifiantTo() {
        return identifiantTo;
    }

    public void setIdentifiantTo(String identifiantTo) {
        this.identifiantTo = identifiantTo;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // Getters and Setters
}

