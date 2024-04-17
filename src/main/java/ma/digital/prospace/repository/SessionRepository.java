package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Session;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByTransactionId(String transactionId);
}