package ma.digital.prospace.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "Invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull
    @Column(name = "token")
    private String token;
    @Column(name = "typeIdentifiantTo")
    private String typeIdentifiantTo;
    @Column(name = "identifiantTo")
    private String identifiantTo;
    @Column(name = "object")
    private String object;
    @Column(name = "dateCreation")
    private Date dateCreation;
    @Column(name = "dateFin")
    private Date dateFin;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatutInvitation status;

    // standard getters and setters

    public enum StatutInvitation {
        PENDING,
        ACCEPTE,
        ANNULE
    }
}

