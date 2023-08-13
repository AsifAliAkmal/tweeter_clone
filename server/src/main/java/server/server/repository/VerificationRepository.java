package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.Verification;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification,Long> {
    Optional<Verification> findByUserId(Long userId);
}
