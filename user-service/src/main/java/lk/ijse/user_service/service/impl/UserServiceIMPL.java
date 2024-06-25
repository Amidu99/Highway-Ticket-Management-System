package lk.ijse.user_service.service.impl;

import lk.ijse.user_service.dto.UserDTO;
import lk.ijse.user_service.repo.UserRepo;
import lk.ijse.user_service.service.UserService;
import lk.ijse.user_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepo repo;
    private final Mapping mapping;

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        repo.save(mapping.toUserEntity(userDTO));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapping.toUserDTO(repo.getUserByEmail(email));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.toUserDTOList(repo.findAll());
    }
}