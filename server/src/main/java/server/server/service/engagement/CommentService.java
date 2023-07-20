package server.server.service.engagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.CommentDTO;
import server.server.model.Comments;
import server.server.model.Engagement;
import server.server.model.Tweets;
import server.server.model.User;
import server.server.repository.CommentsRepository;
import server.server.service.tweets.TweetsService;
import server.server.service.user.UserService;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentsRepository repository;

    @Autowired
    EngagementService engagementService;

    @Autowired
    TweetsService tweetsService;

    @Autowired
    UserService userService;


    public boolean addComment(CommentDTO commentDTO){
        if(commentDTO.getComment() == null || commentDTO.getTweetId() == null || commentDTO.getUserId() == null){
            return false;
        }
        Comments comments = new Comments();
        comments.setComment(commentDTO.getComment());
        User user = userService.findById(commentDTO.getUserId());
        if(user == null){
            return false;
        }
        comments.setUser(user);
        Tweets tweets = tweetsService.findById(commentDTO.getTweetId());
        if(tweets == null){
            return false;
        }
        comments.setTweets(tweets);
        repository.save(comments);
        engagementService.incrementComment(commentDTO.getTweetId());
        return true;
    }

    public boolean removeComment(CommentDTO commentDTO){
        if(commentDTO.getTweetId() == null || commentDTO.getUserId() == null){
            return false;
        }
        List<Comments> byUserIdAndTweetsId = repository.findByUserIdAndTweetsId(commentDTO.getUserId(), commentDTO.getTweetId());
        if(byUserIdAndTweetsId.isEmpty()){
            return false;
        }
        engagementService.decrementComment(commentDTO.getTweetId());
        repository.deleteAll(byUserIdAndTweetsId);
        return true;
    }
}
