package ma.digital.prospace.service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the {@link ma.digital.prospace.domain.Procuration} entity.
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcurationDTO implements Serializable {

    private Long id;

    private String nom;

    private Date dateEffet;

    private Date dateFin;

    private CompteProDTO gestionnaireEspacePro;

    private CompteProDTO utilisateurPro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Date dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public CompteProDTO getGestionnaireEspacePro() {
        return gestionnaireEspacePro;
    }

    public void setGestionnaireEspacePro(CompteProDTO gestionnaireEspacePro) {
        this.gestionnaireEspacePro = gestionnaireEspacePro;
    }

    public CompteProDTO getUtilisateurPro() {
        return utilisateurPro;
    }

    public void setUtilisateurPro(CompteProDTO utilisateurPro) {
        this.utilisateurPro = utilisateurPro;
    }
}
