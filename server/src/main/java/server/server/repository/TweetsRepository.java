package server.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.server.model.Tweets;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets,Long> {
    List<Tweets> findByUserIdInAndOrderByCreateAt(List<Long> userIds);
    Optional<Tweets> findByUserIdAndId(Long userId,Long tweetId);
}
