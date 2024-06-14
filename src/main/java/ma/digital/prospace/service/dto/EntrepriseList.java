package ma.digital.prospace.service.dto;

import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.Statut;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class EntrepriseList {

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
