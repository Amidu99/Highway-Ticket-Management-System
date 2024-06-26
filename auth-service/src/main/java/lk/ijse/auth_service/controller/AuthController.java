package lk.ijse.auth_service.controller;

import lk.ijse.auth_service.secureAndResponse.response.JwtAuthResponse;
import lk.ijse.auth_service.secureAndResponse.secure.SignIn;
import lk.ijse.auth_service.secureAndResponse.secure.SignUp;
import lk.ijse.auth_service.service.AuthenticationService;
import lk.ijse.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/health")
    public String checkAuthHealth() {
        logger.info("Auth Health Test Passed.");
        return "Auth health ok.";
    }

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthResponse> signup(@RequestBody SignUp signup) {
        boolean isExistsUser = userService.existsByEmail(signup.getEmail());

        if (isExistsUser) {
            logger.info("This Email is already exists.");
            return ResponseEntity.status(205).build();
        }

        SignUp newSignUp = new SignUp("", signup.getName(), signup.getEmail(), signup.getPhone(), signup.getAddress(), signup.getPassword());
        logger.info(signup.getEmail()+" : user saved.");
        return ResponseEntity.ok(authenticationService.signUp(newSignUp));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthResponse> signIn( @RequestBody SignIn signIn ) {
        logger.info(signIn.getEmail()+" : user signed successfully.");
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh( @RequestParam ("refreshToken") String refreshToken ) {
        logger.info("Refresh Token : "+refreshToken);
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}