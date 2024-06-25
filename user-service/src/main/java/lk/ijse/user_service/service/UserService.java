package lk.ijse.user_service.service;

import lk.ijse.user_service.dto.UserDTO;
import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);
    void saveUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    void updateUser(UserDTO userDTO);
}