package server.server.service.engagement;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dto.CommentDTO;
import server.server.exception.BadRequestException;
import server.server.exception.ResourceNotFoundException;
import server.server.model.Comments;
import server.server.model.Engagement;
import server.server.model.Tweets;
import server.server.model.User;
import server.server.repository.CommentsRepository;
import server.server.service.tweets.TweetsService;
import server.server.service.user.UserService;

import java.util.List;

@Service
@Slf4j
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
            log.error("CommentService -> addComment input data is not correct.");
            throw new BadRequestException("Something went wrong.");
        }
        Comments comments = new Comments();
        comments.setComment(commentDTO.getComment());
        User user = userService.findById(commentDTO.getUserId());
        if(user == null){
            log.error("CommentService -> addComment  failed to fetch commenting user");
            throw new ResourceNotFoundException("Commenting user does not exist.");
        }
        comments.setUser(user);
        Tweets tweets = tweetsService.findById(commentDTO.getTweetId());
        if(tweets == null){
            log.error("CommentService -> addComment  failed to fetch tweet.");
            throw new ResourceNotFoundException("Tweet does not exist.");
        }
        comments.setTweets(tweets);
        repository.save(comments);
        engagementService.incrementComment(commentDTO.getTweetId());
        return true;
    }

    public boolean removeComment(CommentDTO commentDTO){
        if(commentDTO.getTweetId() == null || commentDTO.getUserId() == null){
            log.error("CommentService -> removeComment input data is not correct.");
            throw new BadRequestException("Something went wrong.");
        }
        List<Comments> byUserIdAndTweetsId = repository.findByUserIdAndTweetsId(commentDTO.getUserId(), commentDTO.getTweetId());
        if(byUserIdAndTweetsId.isEmpty()){
            log.error("CommentService -> removeComment no comment found.");
            throw new BadRequestException("No comment found.");
        }
        engagementService.decrementComment(commentDTO.getTweetId());
        repository.deleteAll(byUserIdAndTweetsId);
        return true;
    }
}
