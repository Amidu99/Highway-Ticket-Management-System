package lk.ijse.vehicle_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements SuperDTO {
    private String vehicleNo;
    private String make;
    private String type;
    private String model;
    private String ownerEmail;
}