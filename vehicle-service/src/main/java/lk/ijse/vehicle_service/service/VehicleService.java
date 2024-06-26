package lk.ijse.vehicle_service.service;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import java.util.List;

public interface VehicleService {
    boolean existsByVehicleNo(String vehicleNo);
    void saveVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleByVehicleNo(String vehicleNo);
    List<VehicleDTO> getAllVehicles();
    void updateVehicle(VehicleDTO vehicleDTO);
}