package lk.ijse.vehicle_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "vehicles")
public class Vehicle {
    @Id
    private String vehicleNo;
    private String make;
    private String type;
    private String model;
    private String ownerEmail;
}