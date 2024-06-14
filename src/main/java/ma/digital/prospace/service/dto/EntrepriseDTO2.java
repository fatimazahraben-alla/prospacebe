package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
/**
 * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
public class EntrepriseDTO2 implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
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

    @Override
    public String toString() {
        return "EntrepriseDTO2{" +
                "id='" + id + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
}
