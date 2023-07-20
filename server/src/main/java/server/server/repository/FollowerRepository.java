package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.Follower;

public interface FollowerRepository extends JpaRepository<Follower,Long> {
    Follower findByUserId(Long userId);
}
