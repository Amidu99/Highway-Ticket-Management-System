package lk.ijse.user_service.controller;

import lk.ijse.user_service.dto.UserDTO;
import lk.ijse.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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