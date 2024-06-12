package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.Entreprise} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntrepriseDTO implements Serializable {

    private UUID id;
    private String etat;
    private String CompteId;

     private String mandataire;

    public String getMandataire() {
        return mandataire;
    }

    public void setMandataire(String mandataire) {
        this.mandataire = mandataire;
    }

    public String getCompteId() {
        return CompteId;
    }

    public void setCompteId(String compteId) {
        CompteId = compteId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntrepriseDTO)) {
            return false;
        }

        EntrepriseDTO entreprise = (EntrepriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entreprise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

}