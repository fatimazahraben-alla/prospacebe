package ma.digital.prospace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import ma.digital.prospace.domain.enumeration.StatutInvitation;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "invitation")
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_piece")
    private String typePiece;

    @Column(name = "numero_piece")
    private String numeroPiece;

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