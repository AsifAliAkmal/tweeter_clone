package server.server.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity(name="follower")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="following")
    private String following;
}
