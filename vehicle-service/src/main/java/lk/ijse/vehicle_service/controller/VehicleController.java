package lk.ijse.vehicle_service.controller;

import lk.ijse.vehicle_service.dto.UserDTO;
import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    final static Logger logger = LoggerFactory.getLogger(VehicleController.class);
    private final VehicleService vehicleService;
    private final RestTemplate restTemplate;

    @GetMapping("health")
    public String checkVehicleHealth(){
        logger.info("Vehicle Health Test Passed.");
        return "Vehicle health ok.";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            validateVehicle(vehicleDTO);
            if (vehicleService.existsByVehicleNo(vehicleDTO.getVehicleNo())) {
                logger.info("This Vehicle is already exists.");
                return ResponseEntity.badRequest().body("This Vehicle is already exists.");
            }
            vehicleService.saveVehicle(vehicleDTO);
            logger.info(vehicleDTO.getVehicleNo()+" : vehicle saved.");
            return ResponseEntity.ok().body(vehicleDTO.getVehicleNo()+" : vehicle saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{vehicleNo}")
    public ResponseEntity<?> getOneVehicle(@PathVariable String vehicleNo) {
        boolean isExists = vehicleService.existsByVehicleNo(vehicleNo);
        if (!isExists){
            logger.info(vehicleNo + " : Vehicle does not exist.");
            return ResponseEntity.noContent().build();
        }
        VehicleDTO vehicleDTO = vehicleService.getVehicleByVehicleNo(vehicleNo);
        return ResponseEntity.ok(vehicleDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllVehicles() {
        List<VehicleDTO> allVehicles = vehicleService.getAllVehicles();
        logger.info("No of all vehicles: "+allVehicles.size());
        if (allVehicles.size() == 0) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(allVehicles);
    }

    @GetMapping("/getAllByEmail")
    public ResponseEntity<?> getAllVehiclesByEmail(@RequestParam String email) {
        List<VehicleDTO> allVehicles = vehicleService.getAllVehiclesByEmail(email);
        logger.info("No of all vehicles: "+allVehicles.size());
        if (allVehicles.size() == 0) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(allVehicles);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            validateVehicle(vehicleDTO);
            boolean isExists = vehicleService.existsByVehicleNo(vehicleDTO.getVehicleNo());
            if (!isExists) {
                logger.info(vehicleDTO.getVehicleNo() + " : Vehicle does not exist.");
                return ResponseEntity.noContent().build();
            }
            vehicleService.updateVehicle(vehicleDTO);
            logger.info(vehicleDTO.getVehicleNo()+" : Vehicle updated.");
            return ResponseEntity.ok().body(vehicleDTO.getVehicleNo()+" : Vehicle updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{vehicleNo}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleNo) {
        boolean isExists = vehicleService.existsByVehicleNo(vehicleNo);
        if (!isExists){
            logger.info(vehicleNo + " : Vehicle does not exist.");
            return ResponseEntity.noContent().build();
        }
        vehicleService.deleteVehicleByVehicleNo(vehicleNo);
        logger.info(vehicleNo+" : Vehicle deleted.");
        return ResponseEntity.ok().body(vehicleNo+" : Vehicle deleted.");
    }

    private void validateVehicle(VehicleDTO vehicleDTO) {
        if (!Pattern.compile("^[A-Za-z\\s]{3,}$").matcher(vehicleDTO.getMake()).matches()) {
            throw new RuntimeException("Invalid Make.");
        }
        if (!Pattern.compile("^[A-Za-z\\s]{3,}$").matcher(vehicleDTO.getType()).matches()) {
            throw new RuntimeException("Invalid Type.");
        }
        if (!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(vehicleDTO.getOwnerEmail()).matches()) {
            throw new RuntimeException("Invalid Email.");
        }
        String url = "http://user-service/api/v1/user/get";
        HttpHeaders headers = new HttpHeaders();
        headers.set("email", vehicleDTO.getOwnerEmail());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("No user found with this email.");
        }
        logger.info("Vehicle validated.");
    }
}