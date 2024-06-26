package lk.ijse.user_service.controller;

import lk.ijse.user_service.dto.UserDTO;
import lk.ijse.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/health")
    public String checkUserHealth() {
        logger.info("User Health Test Passed.");
        return "User health ok.";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        try {
            validateUser(userDTO);
            if (userService.existsByEmail(userDTO.getEmail())) {
                logger.info("This Email is already exists.");
                return ResponseEntity.badRequest().body("This Email is already exists.");
            }
            userService.saveUser(userDTO);
            logger.info(userDTO.getEmail()+" : user saved.");
            return ResponseEntity.ok().body(userDTO.getEmail()+" : user saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getOneUser(@RequestHeader String email) {
        boolean isExists = userService.existsByEmail(email);
        if (!isExists){
            logger.info(email + " : User does not exist.");
            return ResponseEntity.noContent().build();
        }
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        logger.info("No of all users: "+allUsers.size());
        if (allUsers.size() == 0) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(allUsers);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        try {
            validateUser(userDTO);
            boolean isExists = userService.existsByEmail(userDTO.getEmail());
            if (!isExists) {
                logger.info(userDTO.getEmail() + " : User does not exist.");
                return ResponseEntity.noContent().build();
            }
            userService.updateUser(userDTO);
            logger.info(userDTO.getEmail()+" : User updated.");
            return ResponseEntity.ok().body(userDTO.getEmail()+" : User updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader String email) {
        boolean isExists = userService.existsByEmail(email);
        if (!isExists){
            logger.info(email + " : User does not exist.");
            return ResponseEntity.noContent().build();
        }
        userService.deleteUserByEmail(email);
        logger.info(email+" : user deleted.");
        return ResponseEntity.ok().body(email+" : user deleted.");
    }

    private void validateUser(UserDTO userDTO) {
        if (!Pattern.compile("^[A-Za-z\\s]{3,}$").matcher(userDTO.getName()).matches()) {
            throw new RuntimeException("Invalid Name.");
        }
        if (!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(userDTO.getEmail()).matches()) {
            throw new RuntimeException("Invalid Email.");
        }
        logger.info("User validated.");
    }
}