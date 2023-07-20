package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.server.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.email = :userId or u.poneNumber = :userId or u.userName = :userId")
    User findByUserId(String userId);
}
