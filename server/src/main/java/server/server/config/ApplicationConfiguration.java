package server.server.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.server.repository.UserRepository;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    UserRepository userRepository;
    @Bean
    public UserDetailsService getUserDetailsService(){
        return username -> userRepository.findByUserId(username);
    }

    @Bean
    public PasswordEncoder getPasswordEncode(){
        return new BCryptPasswordEncoder();
    }
}

