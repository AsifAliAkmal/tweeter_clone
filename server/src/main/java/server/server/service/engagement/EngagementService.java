package server.server.service.engagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.server.model.Engagement;
import server.server.payload.ApiResponse;
import server.server.repository.EngagementRepository;

@Service
public class EngagementService {

    @Autowired
    EngagementRepository repository;

    public Engagement saveToDB(Engagement engagement){
        return repository.save(engagement);
    }

    @Transactional
    public void incrementLikes(Long tweet_id){
        repository.incrementLike(tweet_id);
    }

    @Transactional
    public void decrementLikes(Long tweet_id){
       repository.decrementLike(tweet_id);
    }

    @Transactional
    public void incrementComment(Long tweet_id){
        repository.incrementComment(tweet_id);
    }

    @Transactional
    public void decrementComment(Long tweet_id){
        repository.decrementComment(tweet_id);
    }

    public Engagement findByTweetId(Long tweetId){
        return repository.findByTweetsId(tweetId);
    }
}
