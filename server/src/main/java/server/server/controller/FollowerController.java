package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.service.tweets.FollowerService;

@RestController
public class FollowerController {

    @Autowired
    FollowerService followerService;

    @PutMapping ("/follow")
    public ResponseEntity<String> addFollowing(@RequestParam("userId")Long userId,@RequestParam("followingId") Long followingId){
        followerService.addFollowing(userId,followingId);
        return new ResponseEntity<>("Follow successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> removeFollowing(@RequestParam("userId")Long userId,@RequestParam("followingId") Long followingId){
        followerService.removeFollowing(userId,followingId);
        return new ResponseEntity<>("Un-Followed successfully.", HttpStatus.OK);
    }
}
