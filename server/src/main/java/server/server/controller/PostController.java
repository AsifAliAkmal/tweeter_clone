package server.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dto.TweetResponseDTO;
import server.server.dto.TweetsDTO;
import server.server.payload.ApiResponse;
import server.server.service.tweets.TweetPostService;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class PostController {

    @Autowired
    TweetPostService tweetPostService;

    @PostMapping("/create_tweet")
    public ResponseEntity<String> createTweet(@RequestBody TweetsDTO tweetsDTO){
        ApiResponse response = tweetPostService.createTweet(tweetsDTO);
        if(response.isSuccess()){
            return new ResponseEntity<>(response.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to create tweet.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get_tweet/{userId}")
    public ResponseEntity<List<TweetResponseDTO>> getTweet(@PathVariable("userId") Long userId){
            if(userId == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(tweetPostService.getTweet(userId),HttpStatus.OK);
    }

    @DeleteMapping("/delete_tweet/{tweet_id}")
    public ResponseEntity<String> deleteTweet(@PathVariable("tweet_id") Long tweetId,@RequestParam("userId") Long userId){
        if(tweetId == null || userId == null){
            return new ResponseEntity<>("Pleaser provide correct data.",HttpStatus.BAD_REQUEST);
        }
        ApiResponse apiResponse = tweetPostService.deleteTweet(tweetId,userId);
        if(apiResponse.isSuccess()){
            return new ResponseEntity<>(apiResponse.getMessage(),HttpStatus.OK);
        }
        return new ResponseEntity<>(apiResponse.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
