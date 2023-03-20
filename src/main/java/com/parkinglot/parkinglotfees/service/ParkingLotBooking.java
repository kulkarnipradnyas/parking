package com.parkinglot.parkinglotfees.service;

import com.parkinglot.parkinglotfees.entity.VehicleType;
import com.parkinglot.parkinglotfees.model.ParkedResponse;

import java.util.Map;


public interface ParkingLotBooking {
 public  Map<String, String> markSpotBooking(String orgName, VehicleType vehicleType);

 public Map<String, String> unMarkSpotBooking(String receiptNumber);
}
