package server.server.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.server.dto.UserInfoDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceTest {

    @Autowired
    UserDetailsService userDetailsService;

    @ParameterizedTest(name="email={0},password={1},phone={2},username={3},name={4},dob={5}")
    @CsvFileSource(resources = "/create_user.csv")
    void createUser(String email,String password,String phone,String username,String name,String dob) {
        UserInfoDTO userInfoDTO = new UserInfoDTO(email,password,phone,username,name,dob);
        assertEquals("Successfully created.",userDetailsService.createUser(userInfoDTO).getMessage());
    }
}