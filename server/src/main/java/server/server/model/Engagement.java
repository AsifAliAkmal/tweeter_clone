package server.server.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="engagement")
@Entity
public class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment_count")
    private Long commentCount;

    @Column(name="like_count")
    private Long likeCount;

    @OneToOne
    @JoinColumn(name="tweet_id")
    private Tweets tweets;
}
