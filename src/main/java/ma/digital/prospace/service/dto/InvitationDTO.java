package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.StatutInvitation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
@Component
public class InvitationDTO {
    private Long id;
    private String typePiece;
    private String numeroPiece;
    private String mail;
    private String tel;
    private StatutInvitation statut;
    private Long compteProId;

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

    public Long getCompteProId() {
        return compteProId;
    }

    public void setCompteProId(Long compteProId) {
        this.compteProId = compteProId;
    }
}


