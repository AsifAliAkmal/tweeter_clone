package server.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_verification")
@Data
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "email_token_expiry")
    private Long emailTokenExpiry;

    @Column(name = "phone_verification_code")
    private Integer code;

    @Column(name = "phone_code_expiry")
    private Long phoneCodeExpiry;
}
