package server.server.service.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.server.Enum.UserStatus;
import server.server.Enum.VerifyStatus;
import server.server.dto.UserInfoDTO;
import server.server.exception.BadRequestException;
import server.server.model.User;
import server.server.model.UserDetails;
import server.server.payload.ApiResponse;
import server.server.repository.UserDetailsRepository;

@Service
@Slf4j
public class UserDetailsService {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResponse createUser(UserInfoDTO userInfoDTO){
        if(userInfoDTO.getUserName() == null || userInfoDTO.getUserName().isEmpty()){
            log.error("UserDetailsService -> createUser username is not present.");
            throw new BadRequestException("please provide username.");
        }else if(userInfoDTO.getName() == null || userInfoDTO.getName().isEmpty()){
            log.error("UserDetailsService -> createUser name is not present.");
            throw new BadRequestException("Please provide name.");
        }else if(userInfoDTO.getPhone() == null || userInfoDTO.getPhone().isEmpty()){
            log.error("UserDetailsService -> createUser phone is not present.");
            throw new BadRequestException("Please provide phone number.");
        }else if(userInfoDTO.getEmail() == null || userInfoDTO.getEmail().isEmpty()){
            log.error("UserDetailsService -> createUser email is not present.");
            throw new BadRequestException("Please provide email.");
        }else if(userInfoDTO.getDob() == null || userInfoDTO.getDob().isEmpty()){
            log.error("UserDetailsService -> createUser dob is not present.");
            throw new BadRequestException("Please provide date-of-birth.");
        }
        User user = userService.getUserByUserId(userInfoDTO.getUserName());
        if(user != null){
            log.error("UserDetailsService -> createUser user already exist.");
            throw new BadRequestException("User already exist.");
        }
        user = new User();
        user.setUserName(userInfoDTO.getUserName());
        user.setUserStatus(UserStatus.ACTIVE);
        user.setEmail(userInfoDTO.getEmail());
        user.setName(userInfoDTO.getName());
        user.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        user.setPoneNumber(userInfoDTO.getPhone());

        user = userService.saveToDB(user);

        UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);
        userDetails.setDateOfBirth(userInfoDTO.getDob());
        userDetails.setEmailVerifyStatus(VerifyStatus.PENDING);
        userDetails.setPhoneVerifyStatus(VerifyStatus.PENDING);
        repository.save(userDetails);
        return new ApiResponse(true,"Successfully created.");
    }
}
