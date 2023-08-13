package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.UserDetails;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
    Optional<UserDetails> findByUserId(Long userId);
}

