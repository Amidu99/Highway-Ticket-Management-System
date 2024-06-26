package lk.ijse.auth_service.util;

import lk.ijse.auth_service.dto.UserDTO;
import lk.ijse.auth_service.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;

    // User Mapping
    public UserDTO toUserDTO(User user) {
        return  mapper.map(user, UserDTO.class);
    }
    public User toUserEntity(UserDTO userDTO) {
        return  mapper.map(userDTO, User.class);
    }
    public List<UserDTO> toUserDTOList(List<User> users) {
        return mapper.map(users, List.class);
    }
}