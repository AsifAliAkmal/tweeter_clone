package server.server.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.server.dto.UserCredentialDTO;
import server.server.model.User;
import server.server.payload.ApiResponse;
import server.server.repository.TokenRepository;
import server.server.repository.UserRepository;
import server.server.service.auth.JwtService;
import server.server.service.token.TokenService;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    TokenService tokenService;

    public User saveToDB(User user){
        return repository.save(user);
    }

    public User getUserByUserId(String userId){
        return repository.findByUserId(userId);
    }

    public ApiResponse authenticateUser(UserCredentialDTO credentialDTO){
        User user = repository.findByUserId(credentialDTO.getUserId());
        if(user == null){
            return new ApiResponse(false,"No user found in oue db.");
        }
        if(!passwordEncoder.matches(credentialDTO.getPassword(), user.getPassword())){
            return new ApiResponse(false,"Password is not correct.");
        }
        String token = jwtService.generateToken(user);
        Long userId = user.getId();
        tokenService.revokeAllForUserId(userId);
        tokenService.saveTokenForUserId(userId,token);
        return new ApiResponse(true,token);
    }

    public User findById(Long id){
        return repository.findById(id).orElse(null);
    }
}
