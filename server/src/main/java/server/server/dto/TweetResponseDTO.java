package server.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.server.model.Engagement;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponseDTO {
    private Long tweetId;
    private String content;
    private Long comments;
    private Long likes;
}
