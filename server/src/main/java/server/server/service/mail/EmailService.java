package server.server.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String[] to,String subject,String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("asifali11813047@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }


    public void sendEmailVerificationLink(String email,String body){
        sendEmail(new String[]{email},"Testing JavaMailSender is working or not.",body);
    }
}
