package lk.ijse.auth_service.service;

import lk.ijse.auth_service.dto.UserDTO;
import lk.ijse.auth_service.secureAndResponse.response.JwtAuthResponse;
import lk.ijse.auth_service.secureAndResponse.secure.SignIn;
import lk.ijse.auth_service.secureAndResponse.secure.SignUp;

public interface AuthenticationService {
    JwtAuthResponse signUp(SignUp newSignUp);
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse refreshToken(String refreshToken);
    boolean matchPassword(UserDTO userDTO);
    void updatePassword(UserDTO userDTO);
}