package ma.digital.prospace.repository;

import ma.digital.prospace.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    // You can define custom query methods here
}

