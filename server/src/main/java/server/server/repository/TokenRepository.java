package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByUserIdAndExpiredAndRevoked(Long userId, boolean isExpired, boolean isRevoked);

}
