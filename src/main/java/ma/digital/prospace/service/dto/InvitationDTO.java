package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

@Component
public class InvitationDTO {
    private Long id;
    private String typePiece;
    private String numeroPiece;
    private String mail;
    private String tel;
    private StatutInvitation statut;
    private UUID compteProId;

    private UUID compteProDestinataireId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
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

    public StatutInvitation getStatut() {
        return statut;
    }

    public void setStatut(StatutInvitation statut) {
        this.statut = statut;
    }

    public UUID getCompteProId() {
        return compteProId;
    }

    public void setCompteProId(UUID compteProId) {
        this.compteProId = compteProId;
    }

    public UUID getCompteProDestinataireId() {
        return compteProDestinataireId;
    }

    public void setCompteProDestinataireId(UUID compteProDestinataireId) {
        this.compteProDestinataireId = compteProDestinataireId;
    }
}