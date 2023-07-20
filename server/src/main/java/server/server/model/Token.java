package server.server.model;

import jakarta.persistence.*;
import lombok.Data;
import server.server.Enum.TokenType;

@Data
@Table
@Entity(name="token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="token")
    private String token;

    @Column(name="token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name="expired")
    private boolean expired = false;

    @Column(name="revoked")
    private boolean revoked = false;

    @Column(name="user_id")
    private Long userId;

}
