package server.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.server.Enum.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  UserInfoDTO {
    private String email;
    private String password;
    private String phone;
    private String userName;
    private String name;
    private String dob;
    private Role role;
}
