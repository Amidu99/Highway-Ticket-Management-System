package lk.ijse.user_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("health")
    public String checkUserHealth(){
        return "User health ok.";
    }
}