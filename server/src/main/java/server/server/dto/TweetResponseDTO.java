package server.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.server.model.Engagement;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponseDTO {
    private Long tweetId;
    private String content;
    private Long comments;
    private Long likes;
    private Instant createdAt;
}
