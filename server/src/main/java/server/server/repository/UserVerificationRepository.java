package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.server.model.UserVerification;

import java.util.Optional;

@Repository
public interface UserVerificationRepository  extends JpaRepository<UserVerification,Long> {
    Optional<UserVerification> findByEmailVerificationToken(String token);
}
