package server.server.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.server.Enum.UserStatus;
import server.server.Enum.VerifyStatus;
import server.server.dto.UserInfoDTO;
import server.server.model.User;
import server.server.model.UserDetails;
import server.server.payload.ApiResponse;
import server.server.repository.UserDetailsRepository;

@Service
public class UserDetailsService {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResponse createUser(UserInfoDTO userInfoDTO){
        User user = new User();
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
