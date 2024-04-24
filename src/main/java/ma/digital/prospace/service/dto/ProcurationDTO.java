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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    private String numeroPiece;

    @Email(message = "L'adresse mail est invalide")
    private String mail;
    private String tel;
    private String nom;

    private Instant dateEffet;

    private Instant dateFin;


    private StatutInvitation statut;
    private String gestionnaireEspaceProId;
    private String gestionnaireEspaceProIdentifiant;
    private String utilisateurProId;
    private String utilisateurProIdentifiant;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(Instant dateEffet) {
        this.dateEffet = dateEffet;
    }

    public Instant getDateFin() {
        return dateFin;
    }
    public StatutInvitation getStatut() {
        return statut;
    }

    public void setStatut(StatutInvitation statut) {
        this.statut = statut;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getGestionnaireEspaceProId() {
        return gestionnaireEspaceProId;
    }

    public void setGestionnaireEspaceProId(String gestionnaireEspaceProId) {
        this.gestionnaireEspaceProId = gestionnaireEspaceProId;
    }
    public String getGestionnaireEspaceProIdentifiant() {
        return gestionnaireEspaceProIdentifiant;
    }

    public void setGestionnaireEspaceProIdentifiant(String gestionnaireEspaceProIdentifiant) {
        this.gestionnaireEspaceProIdentifiant = gestionnaireEspaceProIdentifiant;
    }

    public String getUtilisateurProId() {
        return utilisateurProId;
    }

    public void setUtilisateurProId(String utilisateurProId) {
        this.utilisateurProId = utilisateurProId;
    }

    public String getUtilisateurProIdentifiant() {
        return utilisateurProIdentifiant;
    }

    public void setUtilisateurProIdentifiant(String utilisateurProIdentifiant) {
        this.utilisateurProIdentifiant = utilisateurProIdentifiant;
    }
}