package server.server.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import server.server.Enum.VerifyStatus;

import java.time.Instant;

@Data
@Entity
@Table(name="user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="description")
    private String description;

    @Column(name="follower")
    private Integer follower;

    @Lob
    @Column(name="image_url")
    private String imageUrl;

    @Lob
    @Column(name="profile_url")
    private String profileUr;

    @Column(name="email_verification_status")
    @Enumerated(EnumType.STRING)
    private VerifyStatus emailVerifyStatus;

    @Column(name="phone_verification_status")
    @Enumerated(EnumType.STRING)
    private VerifyStatus phoneVerifyStatus;

    @Column(name="dob")
    private String dateOfBirth;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    @CreationTimestamp
    @Column(name="created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Instant updatedAt;
}
