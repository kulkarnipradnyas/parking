package com.parkinglot.parkinglotfees.service.impl;


import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.ParkingLot;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import com.parkinglot.parkinglotfees.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotAction {
    private ParkingLotRepository parkingLotRepository;

    public ParkingLotAction(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }
    @Transactional(readOnly = false)
    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
    @Transactional(readOnly = false)
    public void delete(ParkingLot parkingLot) {
         parkingLotRepository.delete(parkingLot);
    }

    @Transactional(readOnly = true)
    public List<ParkingLot>  findByOrganisationAndVehicleType(Organisation organisation, Vehicle vehicleType){
        return parkingLotRepository.findByOrganisationAndVehicleType(organisation, vehicleType);
    }

    @Transactional(readOnly = true)
    public Optional<ParkingLot> findById(Long receiptNumber){
        return parkingLotRepository.findById(receiptNumber);
    }
}
