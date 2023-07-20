package server.server.service.tweets;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.TweetResponseDTO;
import server.server.dto.TweetsDTO;
import server.server.model.Follower;
import server.server.model.Tweets;
import server.server.payload.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TweetPostService {

    @Autowired
    FollowerService followerService;

    @Autowired
    TweetsService tweetsService;


    public ApiResponse createTweet(TweetsDTO tweetsDTO){
        return tweetsService.createTweet(tweetsDTO);
    }

    public List<TweetResponseDTO> getTweet(Long userId){
        List<TweetResponseDTO> tweetResponseDTOS = new ArrayList<>();
        Follower follower = followerService.findByUserId(userId);
        if(follower == null){
            return tweetResponseDTOS;
        }
        String[] followingIds = follower.getFollowing().split(",");
        List<Long> userIds = new ArrayList<>();
        for(int i = 0 ; i< followingIds.length ; i++){
            userIds.add(Long.parseLong(followingIds[i]));
        }
        return tweetsService.findByUserIdIn(userIds);
    }

    public ApiResponse deleteTweet(Long tweetId, Long userId) {
        Tweets tweets = tweetsService.findByUserIdAndTweetId(userId, tweetId);
        if(tweets == null){
            return new ApiResponse(false,"This tweet is not created by you.");
        }
        tweetsService.deleteTweet(tweets);
        return new ApiResponse(true,"Successfully deleted.");
    }
}
