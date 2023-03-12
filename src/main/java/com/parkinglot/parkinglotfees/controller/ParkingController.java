package com.parkinglot.parkinglotfees.controller;


import com.parkinglot.parkinglotfees.entity.VehicleType;
import com.parkinglot.parkinglotfees.service.ParkingLotBooking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Validated
@CrossOrigin("*")
public class ParkingController {
    private ParkingLotBooking parkingLotBooking;

    public ParkingController(ParkingLotBooking parkingLotBooking) {
        this.parkingLotBooking= parkingLotBooking;
    }

    @PostMapping("/parking")
    public ResponseEntity<Map<String, String>> parkVehicle(@RequestParam("orgName") String orgName, @RequestParam("vehicleType") VehicleType vehicleType) {
        Map<String, String> parkingLot= parkingLotBooking.markSpotBooking(orgName,vehicleType);
       return new ResponseEntity(parkingLot, HttpStatus.OK);
    }

    @PostMapping("/unpark")
    public ResponseEntity< Map<String, String>> unParkVehicle(@RequestParam("receiptNumber") String receiptNumber) {
        Map<String, String> parkingLot= parkingLotBooking.unMarkSpotBooking(receiptNumber);
        return new ResponseEntity(parkingLot, HttpStatus.OK);
    }


}
