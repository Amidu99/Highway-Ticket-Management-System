package lk.ijse.user_service.service;

import lk.ijse.user_service.dto.UserDTO;

public interface UserService {
    boolean existsByEmail(String email);
    void saveUser(UserDTO userDTO);
}