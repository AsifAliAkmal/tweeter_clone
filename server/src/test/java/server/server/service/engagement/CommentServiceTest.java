package server.server.service.engagement;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.server.dto.CommentDTO;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class CommentServiceTest {
        @Autowired
        CommentService commentService;

        @CsvFileSource(resources = "/add_comment.csv")
        @ParameterizedTest(name="userId={0},tweetId={1},comment={2}")
        public void testIncrementComment(String userId,String tweetId,String comment){
            CommentDTO commentDTO = new CommentDTO(null,null,comment);
            if(userId != null){
                commentDTO.setUserId(Long.parseLong(userId));
            }

            if(tweetId != null){
                commentDTO.setTweetId(Long.parseLong(tweetId));
            }
            assertTrue(commentService.addComment(commentDTO));
        }


    @CsvFileSource(resources = "/remove_comment.csv")
    @ParameterizedTest(name="userId={0},tweetId={1}")
    public void removeComment(String userId,String tweetId){
        CommentDTO commentDTO = new CommentDTO(null,null,null);
        if(userId != null){
            commentDTO.setUserId(Long.parseLong(userId));
        }

        if(tweetId != null){
            commentDTO.setTweetId(Long.parseLong(tweetId));
        }
        assertFalse(commentService.removeComment(commentDTO));
    }

}