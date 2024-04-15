package ma.digital.prospace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.domain.enumeration.TypePiece;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "invitation")
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_piece")
    private TypePiece typePiece;

    @Column(name = "numero_piece")
    private String numeroPiece;

    @Email
    @Column(name = "mail")
    private String mail;

    @Column(name = "tel")
    private String tel;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutInvitation statut = StatutInvitation.PENDING;

    @ManyToOne
    @JoinColumn(name = "compte_pro_id")
    private ComptePro comptePro;
    @ManyToOne
    @JoinColumn(name = "compte_pro_destinataire_id")
    private ComptePro compteProDestinataire;



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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public StatutInvitation getStatut() {
        return statut;
    }

    public void setStatut(StatutInvitation statut) {
        this.statut = statut;
    }

    public ComptePro getComptePro() {
        return comptePro;
    }

    public void setComptePro(ComptePro comptePro) {
        this.comptePro = comptePro;
    }
    public ComptePro getCompteProDestinataire() {
        return compteProDestinataire;
    }

    public void setCompteProDestinataire(ComptePro compteProDestinataire) {
        this.compteProDestinataire = compteProDestinataire;
    }

}