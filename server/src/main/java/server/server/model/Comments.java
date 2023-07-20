package server.server.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;

@Data
@Table(name="comments")
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweets tweets;

    @Column(name="comments")
    private String comment;

    @CurrentTimestamp
    @Column(name="created_at")
    private Instant createAt;
}
