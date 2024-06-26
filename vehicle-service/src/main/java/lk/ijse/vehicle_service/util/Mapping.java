package lk.ijse.vehicle_service.util;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;

    // Vehicle Mapping
    public VehicleDTO toVehicleDTO(Vehicle vehicle) {
        return  mapper.map(vehicle, VehicleDTO.class);
    }
    public Vehicle toVehicleEntity(VehicleDTO vehicleDTO) {
        return  mapper.map(vehicleDTO, Vehicle.class);
    }
    public List<VehicleDTO> toVehicleDTOList(List<Vehicle> vehicles) {
        return mapper.map(vehicles, List.class);
    }
}