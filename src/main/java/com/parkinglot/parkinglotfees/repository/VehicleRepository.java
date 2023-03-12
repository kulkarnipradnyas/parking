package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Vehicle findByVehicleType(String vehicleType);

}
