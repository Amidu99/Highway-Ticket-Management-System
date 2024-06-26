package lk.ijse.vehicle_service.service.impl;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.model.Vehicle;
import lk.ijse.vehicle_service.repo.VehicleRepo;
import lk.ijse.vehicle_service.service.VehicleService;
import lk.ijse.vehicle_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService {
    private final VehicleRepo repo;
    private final Mapping mapping;

    @Override
    public boolean existsByVehicleNo(String vehicleNo) {
        return repo.existsByVehicleNo(vehicleNo);
    }

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        repo.save(mapping.toVehicleEntity(vehicleDTO));
    }

    @Override
    public VehicleDTO getVehicleByVehicleNo(String vehicleNo) {
        return mapping.toVehicleDTO(repo.getVehicleByVehicleNo(vehicleNo));
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return mapping.toVehicleDTOList(repo.findAll());
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        Vehicle existingVehicle = repo.getVehicleByVehicleNo(vehicleDTO.getVehicleNo());
        existingVehicle.setMake(vehicleDTO.getMake());
        existingVehicle.setType(vehicleDTO.getType());
        existingVehicle.setModel(vehicleDTO.getModel());
        repo.save(existingVehicle);
    }

    @Override
    public void deleteVehicleByVehicleNo(String vehicleNo) {
        repo.deleteVehicleByVehicleNo(vehicleNo);
    }
}