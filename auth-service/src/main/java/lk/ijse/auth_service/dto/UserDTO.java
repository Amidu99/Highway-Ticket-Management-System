package lk.ijse.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements SuperDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
}