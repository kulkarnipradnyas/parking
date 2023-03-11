package com.parkinglot.parkinglotfees.service;

import com.parkinglot.parkinglotfees.entity.VehicleType;
import org.springframework.stereotype.Service;


public interface ParkingLotBooking {
 public String markSpotBooking(String orgName, VehicleType vehicleType);
}
