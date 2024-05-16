package ma.digital.prospace.domain;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable =true, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name = "compte_pro_id")
    private ComptePro comptePro;
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
    @Column(name = "is_read", nullable = true)
    private boolean isRead = false;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ComptePro getComptePro() {
        return comptePro;
    }

    public void setComptePro(ComptePro comptePro) {
        this.comptePro = comptePro;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
