package com.parkinglot.parkinglotfees.controller;


import com.parkinglot.parkinglotfees.entity.VehicleType;
import com.parkinglot.parkinglotfees.service.ParkingLotBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


@RestController
@Validated
@CrossOrigin("*")
public class ParkingController {
    private ParkingLotBooking parkingLotBooking;

    public ParkingController(ParkingLotBooking parkingLotBooking) {
        this.parkingLotBooking= parkingLotBooking;
    }

    @GetMapping("/parking")
    public ResponseEntity<String> generateParkingTicket(@RequestParam("orgName") String orgName, @RequestParam("vehicleType") VehicleType vehicleType) {

       String msg= parkingLotBooking.markSpotBooking(orgName,vehicleType);
       return new ResponseEntity(msg, HttpStatus.OK);
    }


}
