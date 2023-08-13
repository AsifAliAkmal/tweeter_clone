package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.service.verification.UserVerificationService;

@RestController
@RequestMapping("/api/verify/")
public class VerificationController {

    @Autowired
    UserVerificationService userVerificationService;
    @GetMapping("/email/{userId}")
    public ResponseEntity<String> sendEmailVerification(@PathVariable("userId") Long userId){
            userVerificationService.generateVerificationLink(userId);
            return new ResponseEntity<>("Verification link sent to your mail.", HttpStatus.OK);
    }

    @GetMapping("/email_verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        userVerificationService.verifyEmail(token);
        return new ResponseEntity<>("Verifed Successfully.",HttpStatus.OK);
    }
}
