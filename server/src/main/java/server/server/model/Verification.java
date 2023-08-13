package server.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="verification")
@Data
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String token;
    private Integer code;

}
