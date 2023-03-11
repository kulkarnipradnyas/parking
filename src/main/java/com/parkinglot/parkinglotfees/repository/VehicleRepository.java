package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.Vehicle;
import com.parkinglot.parkinglotfees.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Vehicle findByVehicleType(String vehicleType);
    List<Vehicle> findAll();
}
