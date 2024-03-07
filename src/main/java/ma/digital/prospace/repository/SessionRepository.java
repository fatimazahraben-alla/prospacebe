package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
