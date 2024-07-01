package lk.ijse.vehicle_service.repo;

import lk.ijse.vehicle_service.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VehicleRepo extends MongoRepository<Vehicle, String> {
    boolean existsByVehicleNo(String vehicleNo);
    Vehicle getVehicleByVehicleNo(String vehicleNo);
    void deleteVehicleByVehicleNo(String vehicleNo);
    List<Vehicle> getAllVehiclesByOwnerEmail(String email);
}