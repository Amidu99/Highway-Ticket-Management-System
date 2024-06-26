package lk.ijse.vehicle_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements SuperDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
}