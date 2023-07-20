package server.server.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import server.server.service.token.TokenService;

@Component
public class CustomLogOutHandler implements LogoutHandler {

    @Autowired
    TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authToken = request.getHeader("Authorization");
        if(authToken == null || authToken.isEmpty() || !authToken.startsWith("Bearer ")){
            return;
        }

        String jwt = authToken.substring(7);
        tokenService.tokenExpired(jwt);
    }
}
