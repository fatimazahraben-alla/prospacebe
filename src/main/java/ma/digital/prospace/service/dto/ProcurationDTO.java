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
    private typeidentifiant typePiece;
    private String numeroPiece;
    private String nom;
    private String prenom;
    private String gestionnaireEspaceProId;
    private String utilisateurProId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public typeidentifiant getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(typeidentifiant typePiece) {
        this.typePiece = typePiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

}