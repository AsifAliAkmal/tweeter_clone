package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.server.dto.CommentDTO;
import server.server.service.engagement.CommentService;
import server.server.service.engagement.EngagementService;

@RestController
@RequestMapping("/api/engage")
public class EngagementController {

    @Autowired
    EngagementService engagementService;

    @Autowired
    CommentService commentService;

    @PutMapping("/inc_like/{tweet_id}")
    public void incrementLike(@PathVariable("tweet_id") Long tweetId){
            engagementService.incrementLikes(tweetId);
    }

    @PutMapping("/dec_like/{tweet_id}")
    public void decrementLike(@PathVariable("tweet_id") Long tweetId){
        engagementService.decrementLikes(tweetId);
    }

    @PostMapping("/add_comment")
    public void addComment(@RequestBody CommentDTO commentDTO){
        commentService.addComment(commentDTO);
    }

    @PostMapping("/remove_comment")
    public void removeComment(@RequestBody  CommentDTO commentDTO){
        commentService.removeComment(commentDTO);
    }
}
