package ma.digital.prospace.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String token;
    private String typeIdentifiantTo;
    private String identifiantTo;
    private String object;
    private Date dateCreation;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private StatutInvitation status;

    // standard getters and setters

    public enum StatutInvitation {
        PENDING,
        ACCEPTE,
        ANNULE
    }
}

