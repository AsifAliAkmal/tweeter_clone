package server.server.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name="tweets")
public class Tweets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "tweets",cascade = CascadeType.ALL)
    private Engagement engagement;

    @OneToMany(mappedBy = "tweets",cascade = CascadeType.ALL)
    private List<Comments> commentsList;

    @CurrentTimestamp
    @Column(name="created_at")
    private Instant createAt;
}
