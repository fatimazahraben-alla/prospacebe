package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.domain.enumeration.TypePiece;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Component
public class InvitationDTO {
    private Long id;
    private TypePiece typePiece;
    private String numeroPiece;

    @Email(message = "L'adresse mail est invalide")
    private String mail;
    private String tel;
    private StatutInvitation statut;
    private Long compteProId;

    private Long compteProDestinataireId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePiece getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(TypePiece typePiece) {
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

    public Long getCompteProDestinataireId() {
        return compteProDestinataireId;
    }

    public void setCompteProDestinataireId(Long compteProDestinataireId) {
        this.compteProDestinataireId = compteProDestinataireId;
    }
}


