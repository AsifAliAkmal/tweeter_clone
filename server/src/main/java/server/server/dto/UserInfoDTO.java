package server.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private String email;
    private String password;
    private String phone;
    private String userName;
    private String name;
    private String dob;
}
