package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.Procuration} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcurationDTO implements Serializable {
    private UUID id;
    private String gestionnaireEspaceProId;
    private String utilisateurProId;
    private String nomUtilisateurPro;
    private String prenomUtilisateurPro;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGestionnaireEspaceProId() {
        return gestionnaireEspaceProId;
    }

    public void setGestionnaireEspaceProId(String gestionnaireEspaceProId) {
        this.gestionnaireEspaceProId = gestionnaireEspaceProId;
    }

    public String getUtilisateurProId() {
        return utilisateurProId;
    }

    public void setUtilisateurProId(String utilisateurProId) {
        this.utilisateurProId = utilisateurProId;
    }

    public StatutInvitation getStatut() {
        return statut;
    }

    public void setStatut(StatutInvitation statut) {
        this.statut = statut;
    }

    private StatutInvitation statut;


    public String getNomUtilisateurPro() {
        return nomUtilisateurPro;
    }

    public void setNomUtilisateurPro(String nomUtilisateurPro) {
        this.nomUtilisateurPro = nomUtilisateurPro;
    }

    public String getPrenomUtilisateurPro() {
        return prenomUtilisateurPro;
    }

    public void setPrenomUtilisateurPro(String prenomUtilisateurPro) {
        this.prenomUtilisateurPro = prenomUtilisateurPro;
    }

}