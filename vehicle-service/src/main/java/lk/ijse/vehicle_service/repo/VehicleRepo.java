package lk.ijse.vehicle_service.repo;

import lk.ijse.vehicle_service.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepo extends MongoRepository<Vehicle, String> {
}