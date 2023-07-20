package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.model.Comments;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {

    public List<Comments> findByUserIdAndTweetsId(Long userId, Long tweetId);
}
