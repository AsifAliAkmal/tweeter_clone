package server.server.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.server.model.Engagement;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement,Long> {

    @Transactional
    @Modifying
    @Query("update Engagement e set e.likeCount = e.likeCount + 1 where e.tweets.id=:tweet_id")
    public void incrementLike(@Param("tweet_id") Long tweet_id);

    @Transactional
    @Modifying
    @Query("update Engagement e set e.likeCount = e.likeCount - 1 where e.tweets.id=:tweet_id")
    public void decrementLike(@Param("tweet_id") Long tweet_id);

    @Transactional
    @Modifying
    @Query("update Engagement e set e.commentCount = e.commentCount + 1 where e.tweets.id=:tweet_id")
    public void incrementComment(@Param("tweet_id") Long tweet_id);

    @Transactional
    @Modifying
    @Query("update Engagement e set e.commentCount = e.commentCount - 1 where e.tweets.id=:tweet_id")
    public void decrementComment(@Param("tweet_id") Long tweet_id);

    Engagement findByTweetsId(Long tweetId);
}
