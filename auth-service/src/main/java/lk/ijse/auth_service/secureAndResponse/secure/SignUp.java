package lk.ijse.auth_service.secureAndResponse.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
}