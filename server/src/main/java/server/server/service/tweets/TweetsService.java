package server.server.service.tweets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.TweetResponseDTO;
import server.server.dto.TweetsDTO;
import server.server.model.Engagement;
import server.server.model.Tweets;
import server.server.model.User;
import server.server.payload.ApiResponse;
import server.server.repository.TweetsRepository;
import server.server.service.engagement.EngagementService;
import server.server.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetsService {

    @Autowired
    TweetsRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    EngagementService engagementService;

    public Tweets saveToDB(Tweets tweets){
        return repository.save(tweets);
    }

    public ApiResponse createTweet(TweetsDTO tweetsDTO){
        User user = userService.findById(tweetsDTO.getUserId());
        Tweets tweets = new Tweets();
        tweets.setUser(user);
        tweets.setMessage(tweetsDTO.getMessage());
        tweets = repository.save(tweets);
        Engagement engagement = new Engagement();
        engagement.setLikeCount(0L);
        engagement.setCommentCount(0L);
        engagement.setTweets(tweets);
        engagementService.saveToDB(engagement);
        return new ApiResponse(true,"Successfully created.");
    }

    public Tweets findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<TweetResponseDTO> findByUserIdIn(List<Long> userIds){
        List<Tweets> tweetsList =  repository.findByUserIdInAndOrderByCreateAt(userIds);
        List<TweetResponseDTO> tweetResponseDTOList = new ArrayList<>();
        for(Tweets tweets : tweetsList){
            TweetResponseDTO tweetResponseDTO = new TweetResponseDTO(tweets.getId(),tweets.getMessage(),
                    tweets.getEngagement().getCommentCount(),tweets.getEngagement().getLikeCount());
            tweetResponseDTOList.add(tweetResponseDTO);
        }
        return tweetResponseDTOList;
    }

    public Tweets findByUserIdAndTweetId(Long userId,Long tweetId){
        return repository.findByUserIdAndId(userId,tweetId).orElse(null);
    }


    public void deleteTweet(Tweets tweets) {
        repository.delete(tweets);
    }
}
