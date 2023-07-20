package server.server.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.server.model.User;
import server.server.service.user.UserService;


@Component
public class CustomAuthenticationProvide implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        User userByUserId = userService.getUserByUserId(userName);

        if(passwordEncoder.matches(password,userByUserId.getPassword())){
            return new UsernamePasswordAuthenticationToken(userByUserId,null,userByUserId.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
