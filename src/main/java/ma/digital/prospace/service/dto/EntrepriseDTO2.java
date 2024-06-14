package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EntrepriseDTO2 implements Serializable {
    private String id;
    private String etat;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
