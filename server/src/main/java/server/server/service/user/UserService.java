package server.server.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.server.dto.UserCredentialDTO;
import server.server.exception.BadRequestException;
import server.server.exception.ResourceNotFoundException;
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
        if(credentialDTO.getUserId() == null || credentialDTO.getUserId().isEmpty() ){
            throw new BadRequestException("Please provide the userId/email/phone_number.");
        }
        if(credentialDTO.getPassword() == null || credentialDTO.getPassword().isEmpty()){
            throw new BadRequestException("Please provide the password");
        }
        User user = repository.findByUserId(credentialDTO.getUserId());
        if(user == null){
            throw  new ResourceNotFoundException("user does not exist.");
        }
        if(!passwordEncoder.matches(credentialDTO.getPassword(), user.getPassword())){
            throw  new BadRequestException("Password is incorrect.");
        }
        String token = jwtService.generateToken(user);
        Long userId = user.getId();
        tokenService.revokeAllForUserId(userId);
        tokenService.saveTokenForUserId(userId,token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ApiResponse(true,token);
    }

    public User findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Boolean isUserNameExist(String userName){
        return repository.existsByUserName(userName);
    }
}
