package server.server.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.repository.VerificationRepository;
import server.server.service.user.UserDetailsService;

@Service
public class VerificationService {
    @Autowired
    VerificationRepository repository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    EmailService emailService;

    public void generateVerificationLink(Long userId){

    }
}
