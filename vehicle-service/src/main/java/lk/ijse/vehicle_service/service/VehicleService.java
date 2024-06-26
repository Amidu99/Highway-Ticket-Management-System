package lk.ijse.vehicle_service.service;

import lk.ijse.vehicle_service.dto.VehicleDTO;

public interface VehicleService {
    boolean existsByVehicleNo(String vehicleNo);
    void saveVehicle(VehicleDTO vehicleDTO);
}