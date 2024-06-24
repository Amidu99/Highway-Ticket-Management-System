package lk.ijse.vehicle_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    @GetMapping("health")
    public String checkUserHealth(){
        return "Vehicle health ok.";
    }
}