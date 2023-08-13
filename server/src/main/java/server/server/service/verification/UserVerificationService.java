package server.server.service.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import server.server.Enum.VerifyStatus;
import server.server.exception.BadRequestException;
import server.server.model.User;
import server.server.model.UserDetails;
import server.server.model.UserVerification;
import server.server.repository.UserVerificationRepository;
import server.server.service.mail.EmailService;
import server.server.service.user.UserDetailsService;
import server.server.service.user.UserService;

import java.util.UUID;

@Service
public class UserVerificationService {

    @Autowired
    UserVerificationRepository repository;

    @Autowired
    EmailService emailService;


    @Autowired
    UserDetailsService userDetailsService;

    @Value("${back-end-url}")
    private String backendUrl;

    public void generateVerificationLink(Long userId){
            String token = UUID.randomUUID().toString();
            String verificationLink = backendUrl+"/api/verify/email_verify?token="+token;
            UserDetails userDetails = userDetailsService.findByUserId(userId);
            if(userDetails == null){
                throw new BadRequestException("Sorry we don't found user.");
            }
            User user = userDetails.getUser();
            UserVerification userVerification = new UserVerification();
            userVerification.setEmailVerificationToken(token);
            userVerification.setEmailTokenExpiry(System.currentTimeMillis()/1000 + 10*60);
            userVerification.setUserId(userId);
            repository.save(userVerification);
            emailService.sendEmailVerificationLink(user.getEmail(),verificationLink);
    }

    public void verifyEmail(String token){
        UserVerification userVerification = repository.findByEmailVerificationToken(token).orElse(null);
        if(userVerification == null){
            throw new BadRequestException("Token doesn't exist");
        }
        if(userVerification.getEmailTokenExpiry() < System.currentTimeMillis()/1000){
            throw new BadRequestException("Token expired, please try again to generate verification link.");
        }
        Long userId = userVerification.getUserId();
        UserDetails userDetails = userDetailsService.findByUserId(userId);
        userDetails.setEmailVerifyStatus(VerifyStatus.VERIFIED);
        userDetailsService.saveToDB(userDetails);
        repository.delete(userVerification);
    }
}
