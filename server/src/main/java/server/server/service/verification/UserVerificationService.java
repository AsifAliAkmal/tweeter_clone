package server.server.service.verification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.server.Enum.VerifyStatus;
import server.server.exception.BadRequestException;
import server.server.model.User;
import server.server.model.UserDetails;
import server.server.model.UserVerification;
import server.server.repository.UserVerificationRepository;
import server.server.service.mail.EmailService;
import server.server.service.user.UserDetailsService;

import java.util.Random;
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

    @Value("${twilio.account-id}")
    private String accountId;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-no}")
    private String phoneNo;

    @PostConstruct
    public void initiateTwilio(){
        Twilio.init(accountId,authToken);
    }

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

    public void generateOTPAndSend(Long userId){
        UserDetails userDetails = userDetailsService.findByUserId(userId);
        if(userDetails == null){
            throw new BadRequestException("User doesn't exist.");
        }
        User user = userDetails.getUser();
        int otp = new Random().nextInt(900000) + 100000 ;
        UserVerification userVerification = new UserVerification();
        userVerification.setUserId(userId);
        userVerification.setOtp(otp);
        userVerification.setOtpExpiry(System.currentTimeMillis()/1000+10*60);
        String body = "Your Twitter Verification Code is :"+otp+". expires in 10 mint.";
        repository.save(userVerification);
        PhoneNumber to = new PhoneNumber(user.getPoneNumber());
        PhoneNumber from = new PhoneNumber(phoneNo);
        Message message = Message.creator(to, from, body).create();
    }

    public void verifyOtp(int otp){
        UserVerification userVerification = repository.findByOtp(otp).orElse(null);
        if(userVerification == null){
            throw new BadRequestException("Invalid OTP!");
        }
        if(userVerification.getOtpExpiry() < System.currentTimeMillis()/1000){
            throw new BadRequestException("OTP expired, please try again.");
        }
        Long userId = userVerification.getUserId();
        UserDetails userDetails = userDetailsService.findByUserId(userId);
        userDetails.setPhoneVerifyStatus(VerifyStatus.VERIFIED);
        userDetailsService.saveToDB(userDetails);
        repository.delete(userVerification);
    }
}
