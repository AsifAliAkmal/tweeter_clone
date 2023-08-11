package server.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.server.dto.UserCredentialDTO;
import server.server.dto.UserInfoDTO;
import server.server.payload.ApiResponse;
import server.server.service.mail.EmailService;
import server.server.service.user.UserDetailsService;
import server.server.service.user.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping()
    public String testCode(){
        return "Success";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserCredentialDTO userCredentialDTO){
        ApiResponse apiResponse = userService.authenticateUser(userCredentialDTO);
        if(apiResponse.isSuccess()){
            return new ResponseEntity<>(apiResponse.getMessage(),HttpStatus.OK);
        }
        return  new ResponseEntity<>(apiResponse.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserInfoDTO userInfoDTO){
        ApiResponse apiResponse = userDetailsService.createUser(userInfoDTO);
        if(apiResponse.isSuccess()){
            return new ResponseEntity<>(apiResponse.getMessage(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to create user",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public void logOut(){
        SecurityContextHolder.clearContext();
    }
}
