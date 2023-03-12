package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.ParkingLot;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository  extends JpaRepository<ParkingLot,Long> {
  List<ParkingLot> findByOrganisationAndVehicleType(Organisation organisation, Vehicle vehicleType);

}
