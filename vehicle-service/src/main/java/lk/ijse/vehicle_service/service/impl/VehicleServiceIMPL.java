package lk.ijse.vehicle_service.service.impl;

import lk.ijse.vehicle_service.repo.VehicleRepo;
import lk.ijse.vehicle_service.service.VehicleService;
import lk.ijse.vehicle_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceIMPL implements VehicleService {
    private final VehicleRepo repo;
    private final Mapping mapping;

}