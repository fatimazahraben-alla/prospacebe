package ma.digital.prospace.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class AuditAssociationDTO implements Serializable {

    private UUID id;
    private String action;
    private UUID associationId;
    private String compteId;
    private Instant timestamp;

    // Constructors
    public AuditAssociationDTO() {
    }

    public AuditAssociationDTO(UUID id, String action, UUID associationId, String compteId, Instant timestamp) {
        this.id = id;
        this.action = action;
        this.associationId = associationId;
        this.compteId = compteId;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UUID getAssociationId() {
        return associationId;
    }

    public void setAssociationId(UUID associationId) {
        this.associationId = associationId;
    }

    public String getCompteId() {
        return compteId;
    }

    public void setCompteId(String compteId) {
        this.compteId = compteId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
