package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
}
