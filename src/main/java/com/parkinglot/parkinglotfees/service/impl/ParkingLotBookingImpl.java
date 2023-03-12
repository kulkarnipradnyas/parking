package com.parkinglot.parkinglotfees.service.impl;

import com.parkinglot.parkinglotfees.entity.*;
import com.parkinglot.parkinglotfees.exception.ErrorCodes;
import com.parkinglot.parkinglotfees.exception.ParkingServiceException;
import com.parkinglot.parkinglotfees.repository.FeeSchemaRepository;
import com.parkinglot.parkinglotfees.repository.OrganisationRepository;
import com.parkinglot.parkinglotfees.repository.VehicleRepository;
import com.parkinglot.parkinglotfees.service.ParkingLotBooking;
import com.parkinglot.parkinglotfees.utils.ParkingUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ParkingLotBookingImpl implements ParkingLotBooking {
    private OrganisationRepository organisationRepository;
    private ParkingLotAction parkingLotRepository;

    private FeeSchemaRepository feeSchemaRepository;
    private VehicleRepository vehicleRepository;

    public ParkingLotBookingImpl(OrganisationRepository organisationRepository, ParkingLotAction parkingLotRepository, FeeSchemaRepository feeSchemaRepository,
                                 VehicleRepository vehicleRepository) {
        this.organisationRepository = organisationRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.feeSchemaRepository = feeSchemaRepository;
        this.vehicleRepository = vehicleRepository;
    }

    private Integer getVehicleCountByOrg(Organisation organisation, String vehicleType) {
        if (vehicleType.equals(VehicleType.BUS.toString())) {
            return organisation.getBusSeat();

        } else if (vehicleType.equals(VehicleType.CAR.toString())) {
            return organisation.getCarSeat();
        }
        return organisation.getMotorCycleSeat();
    }

    @Override
    public  Map<String, String> markSpotBooking(String orgName, VehicleType vehicleType) {
        try {
            Vehicle vehicle = vehicleRepository.findByVehicleType(vehicleType.toString());
            List<Organisation> organisation = organisationRepository.findByName(orgName);
            ParkingLot parkingLot = new ParkingLot();
            LocalDateTime currentDateTime = LocalDateTime.now();
            String formattedDateTime = ParkingUtils.getFormattedDate(currentDateTime);
            if (organisation != null && organisation.size() > 0) {
                Organisation org = organisation.get(0);
                // organisation having below count
                Integer totalCount = getVehicleCountByOrg(org, vehicle.getVehicleType());
                List<ParkingLot> occupiedParkingByVehicleType = parkingLotRepository.findByOrganisationAndVehicleType(org, vehicle);

                // spot, org, entry time and vehicle type while parking
                parkingLot.setOrganisation(org);
                parkingLot.setEntryTime(currentDateTime);
                parkingLot.setVehicleType(vehicle);
                FeeOrgModel feeOrgModel = org.getFeeOrgModels();
                List<FeeSchema> feeSchemaListAsPerVehicleType = feeSchemaRepository.findByFeeOrgModelAndVehicleType(feeOrgModel.getId(),
                        parkingLot.getVehicleType().getId());
                parkingLot.setFeeSchema(feeSchemaListAsPerVehicleType);
                if (occupiedParkingByVehicleType.size() == 0) {
                    parkingLot.setParking_spot_number(1);
                } else {
                    List<Integer> occupiedSeats = occupiedParkingByVehicleType.stream().map(parking -> parking.getParking_spot_number()).collect(Collectors.toList());
                    if (occupiedSeats.size() == 0) {
                        throw new RuntimeException("No space available.");
                    }
                    List<Integer> emptySeats = IntStream.range(1, totalCount).filter(seatNo -> !occupiedSeats.contains(seatNo)).boxed().collect(Collectors.toList());
                    Integer seatNo = emptySeats.get(0);
                    parkingLot.setParking_spot_number(seatNo);
                }
            }
            // save it
            parkingLotRepository.save(parkingLot);
            Map<String, String> data = new HashMap<>();
            data.put("Ticket Number", parkingLot.getId().toString());
            data.put("Spot Number", parkingLot.getParking_spot_number().toString());
            data.put("Entry Date-time", formattedDateTime);
            return data;
        }catch (Exception e){
            throw new ParkingServiceException(String.valueOf(ErrorCodes.E311011),e,"Something went wrong in parking.");
        }

    }

    public static Integer findFeesBySlot(List<Integer> slots, long mints, FeeSchema fee ) {
            boolean isInfinite= slots.get(1).intValue() == Integer.MAX_VALUE;
            int min = slots.get(0) * 60;
            int max = isInfinite ? slots.get(1) : slots.get(1).intValue()* 60;
            Integer hours = (int) Math.floor(mints /60);
            if(min < mints && mints <= max){
                if(!isInfinite){
                   return fee.getFees();
                }
                return (hours * fee.getFees());
            }
           return 0;
    }

    @Override
    public  Map<String, String> unMarkSpotBooking(String receiptNumber) {
        try {
            ParkingLot parkingLot = parkingLotRepository.findById(Long.valueOf(receiptNumber))
                    .orElseThrow(() -> new RuntimeException("Please enter correct recipeNumber."));
            LocalDateTime currentDateTime = LocalDateTime.now();
            String formattedExitDate = ParkingUtils.getFormattedDate(currentDateTime);
            String formattedEntryDate = ParkingUtils.getFormattedDate(parkingLot.getEntryTime());
            LocalDateTime entryTime = parkingLot.getEntryTime();
            long mintsParked = ChronoUnit.MINUTES.between(entryTime, currentDateTime);

            List<FeeSchema> feeSchemaListAsPerVehicleType = parkingLot.getFeeSchema();
            Map<FeeSchema, List<Integer>> feeSchemaMap = feeSchemaListAsPerVehicleType.stream().collect(Collectors.toMap(
                    m -> m,
                    v -> convertStringIntervalToInt(v.getInterval())
            ));
            Map<String, String> data = new HashMap<>();
            data.put("Receipt Number", "R-" + parkingLot.getId().toString());

            data.put("Entry Date-time", formattedEntryDate);
            data.put("Exit Date-time", formattedExitDate);

            for (Map.Entry<FeeSchema, List<Integer>> entry : feeSchemaMap.entrySet()) {
                FeeSchema fee = entry.getKey();
                List<Integer> value = entry.getValue();
                Integer fees = findFeesBySlot(value, mintsParked, fee);
                if (fees != 0) {
                    data.put("Fees", fees.toString());
                }
            }
            if (data.get("Fees") == null) {
                throw new RuntimeException("No fee schema defined");

            }
            parkingLotRepository.delete(parkingLot);
            return data;
        }catch (Exception e){
            throw new ParkingServiceException(String.valueOf(ErrorCodes.E311011),e,"Something went wrong in unparking");
        }
    }

    private List<Integer> convertStringIntervalToInt(String feeInterval) {
        String[] feeArray = feeInterval.replaceAll("\\[", "")
                .replaceAll("]", "")
                .split(",");
        List<Integer> newList = new ArrayList<Integer>(feeArray.length);
        for (String myInt : feeArray) {
            Integer convertedValue = myInt.trim().equalsIgnoreCase("Infinity") ? Integer.MAX_VALUE : Integer.valueOf(myInt.trim());
            newList.add(convertedValue);
        }
        return newList;
    }

}
