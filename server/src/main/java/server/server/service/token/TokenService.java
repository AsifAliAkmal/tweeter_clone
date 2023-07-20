package server.server.service.token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.Enum.TokenType;
import server.server.model.Token;
import server.server.repository.TokenRepository;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    TokenRepository repository;

    public Token findByToken(String jwt){
        return repository.findByToken(jwt).orElse(null);
    }

    public void saveToDB(Token token){
        repository.save(token);
    }


    public void revokeAllForUserId(Long userId){
        List<Token> tokenList = repository.findByUserIdAndExpiredAndRevoked(userId,false,false);
        tokenList.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
            repository.save(token);
        });
    }

    public void saveTokenForUserId(Long userId,String jwt){
        Token token = new Token();
        token.setToken(jwt);
        token.setUserId(userId);
        token.setTokenType(TokenType.BEARER);
        repository.save(token);
    }

    public void tokenExpired(String jwt){
        Token token = repository.findByToken(jwt).orElse(null);
        if(token != null){
            token.setExpired(true);
            token.setRevoked(true);
            repository.save(token);
        }
    }
}
