package ma.digital.prospace.domain;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Session")
public class Session implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "json_data")
    private String jsonData;

    public enum Status {
        IN_PROGRESS,
        COMPLETED
    }


    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", nullable = false)
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public static Session createSession(String transactionId, Date createdAt, String jsonData, Status status) {
        Session session = new Session();
        session.setTransactionId(transactionId);
        session.setCreatedAt(createdAt);
        session.setJsonData(jsonData);
        session.setStatus(status);
        return session;
    }

    // Lire une session par son identifiant
    public static Session getSessionById(EntityManager entityManager, Long id) {
        return entityManager.find(Session.class, id);
    }

    // Mettre à jour une session
    public static void updateSession(EntityManager entityManager, Session session) {
        entityManager.getTransaction().begin();
        entityManager.merge(session);
        entityManager.getTransaction().commit();
    }

    // Supprimer une session
    public static void deleteSession(EntityManager entityManager, Session session) {
        entityManager.getTransaction().begin();
        entityManager.remove(session);
        entityManager.getTransaction().commit();
    }

    // Lister toutes les sessions
    public static List<Session> getAllSessions(EntityManager entityManager) {
        String jpql = "SELECT s FROM Session s";
        TypedQuery<Session> query = entityManager.createQuery(jpql, Session.class);
        return query.getResultList();
    }


}

