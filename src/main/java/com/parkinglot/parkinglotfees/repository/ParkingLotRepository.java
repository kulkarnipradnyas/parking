package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.ParkingLot;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import com.parkinglot.parkinglotfees.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository  extends JpaRepository<ParkingLot,Long> {
  @Query("SELECT p FROM ParkingLot p WHERE p.organisation = :organisation AND p.vehicleType = :vehicleType")

  List<ParkingLot> findByOrganisationInAndVehicleType(@Param("organisation") Organisation organisation,@Param("vehicleType")  VehicleType vehicleType);

  List<ParkingLot> findByOrganisationAndVehicleType(Organisation organisation, Vehicle vehicleType);

}
