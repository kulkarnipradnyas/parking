package com.parkinglot.parkinglotfees.service.impl;

import com.parkinglot.parkinglotfees.entity.*;
import com.parkinglot.parkinglotfees.repository.FeeSchemaRepository;
import com.parkinglot.parkinglotfees.repository.OrganisationRepository;
import com.parkinglot.parkinglotfees.repository.ParkingLotRepository;
import com.parkinglot.parkinglotfees.repository.VehicleRepository;
import com.parkinglot.parkinglotfees.service.ParkingLotBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotBookingImpl implements ParkingLotBooking {
//    @Autowired
    private OrganisationRepository organisationRepository;
    //private ModelMapper mapper;
//    @Autowired
    private ParkingLotRepository parkingLotRepository;

//    @Autowired
    private FeeSchemaRepository feeSchemaRepository;
    private VehicleRepository vehicleRepository;


    public ParkingLotBookingImpl(OrganisationRepository organisationRepository, ParkingLotRepository parkingLotRepository, FeeSchemaRepository feeSchemaRepository,
                                 VehicleRepository vehicleRepository) {
        this.organisationRepository = organisationRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.feeSchemaRepository = feeSchemaRepository;
        this.vehicleRepository = vehicleRepository;
    }
    @PostConstruct
    public void getMyMethod(){

    }

    @Override
    public String markSpotBooking(String orgName, VehicleType vehicleType) {
        Vehicle vehicle= vehicleRepository.findByVehicleType(vehicleType.toString());

        List<Organisation> organisation = organisationRepository.findByName(orgName);

        if(organisation != null && organisation.size() >0){
            Organisation org= organisation.get(0);
           List<ParkingLot> availableParking= parkingLotRepository.findByOrganisationAndVehicleType(org, vehicle);
           if(availableParking.size() == 0){
              ParkingLot parkingLot = new ParkingLot();
              // spot, org, entry time and vehicle type while parking
               parkingLot.setParking_spot_number(1);
               parkingLot.setOrganisation(org);
               LocalDateTime currentDateTime = LocalDateTime.now();
//               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
//               String formattedDateTime = currentDateTime.format(formatter);
               parkingLot.setEntryTime(currentDateTime);
               parkingLot.setVehicleType(vehicle);
               FeeOrgModel feeOrgModel = org.getFeeOrgModels();
               List<FeeSchema> feeSchemas= feeSchemaRepository.findAll();
               System.out.println(feeSchemas + "feeSchemas*****");
               List<FeeSchema> feeSchemaListAsPerVehicleType= feeSchemaRepository.findByFeeOrgModelAndVehicleType(feeOrgModel.getId(), vehicle.getId());

               System.out.println(feeSchemaListAsPerVehicleType + "feeSchemaListAsPerVehicleType");
               String[] a= "AABCCCDD".split("");
               Map<String, Long> s= Arrays.stream(a).collect(Collectors.groupingBy((c)-> c, Collectors.counting()));
               System.out.println(s + "********");


           }else{

           }
            System.out.println("availableParking" + "availableParkingSpotavailableParkingSpot");
        }


        return "success";

    }
    public void assignParking(){

    }
//    private ParkedResponse mapToEntity(ParkingLot parkingLot){
//        ParkedResponse commentDto = mapper.map(parkingLot, ParkingLot.class);
//
//
//        return  commentDto;
//    }
}
