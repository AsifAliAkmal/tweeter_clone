package server.server.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import server.server.Enum.Role;
import server.server.Enum.UserStatus;

import java.time.Instant;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="user")
@Data
public class User implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String poneNumber;

    @Column(name="user_name")
    private String userName;

    @Lob
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToOne(mappedBy = "user")
    private UserDetails userDetails;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Tweets> tweetsList;

    @OneToMany(mappedBy = "user")
    private List<Comments> comments;

    @Enumerated(EnumType.STRING)
    private Role role ;

    @CreationTimestamp
    @Column(name="created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Instant updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
