package com.parkinglot.parkinglotfees.service.impl;

import com.parkinglot.parkinglotfees.entity.*;
import com.parkinglot.parkinglotfees.repository.FeeSchemaRepository;
import com.parkinglot.parkinglotfees.repository.OrganisationRepository;
import com.parkinglot.parkinglotfees.repository.VehicleRepository;
import com.parkinglot.parkinglotfees.utils.CommonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.*;
import org.mockito.stubbing.Answer;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class ParkingLotBookingImplTest {

    @Mock
    private OrganisationRepository organisationRepository;
    @Mock
    private ParkingLotAction parkingLotRepository;
    @Mock
    private FeeSchemaRepository feeSchemaRepository;
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ParkingLotBookingImpl parkingLotBookingImpl;

    private Organisation organisation;
    private ParkingLot parkingLot;
    private Vehicle vehicle;
    private FeeOrgModel feeOrgModel;
    private FeeSchema feeSchema;

    @BeforeEach
    void setup() {
        feeOrgModel= new FeeOrgModel();
        feeOrgModel.setId(1L);
        feeOrgModel.setName("mall");

        organisation = new Organisation();
        organisation.setId(1L);
        organisation.setName("Test Org");
        organisation.setBusSeat(10);
        organisation.setCarSeat(20);
        organisation.setMotorCycleSeat(30);
        organisation.setFeeOrgModels(feeOrgModel);

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setVehicleType("CAR");

        feeSchema = new FeeSchema();
        feeSchema.setId(1L);
        feeSchema.setFees(10);
        feeSchema.setInterval("[0,10]");
        feeSchema.setFeeOrgModel(feeOrgModel);
        feeSchema.setVehicleType(vehicle);

        parkingLot = new ParkingLot(); // initialize with empty constructor
        parkingLot.setId(1L);
        parkingLot.setOrganisation(organisation);
        parkingLot.setEntryTime(LocalDateTime.now());
        parkingLot.setParking_spot_number(1);
        parkingLot.setVehicleType(vehicle);
        parkingLot.setFeeSchema(List.of(feeSchema));



    }

    @Test
    @Transactional
    void testMarkSpotBooking() {
        Map<String, String> expectedData = new HashMap<>();
        expectedData.put("Ticket Number", "1");
        expectedData.put("Spot Number", "2");
        expectedData.put("Entry Date-time", CommonUtils.getFormattedDate(LocalDateTime.now(),null));
        Organisation testOrg = new Organisation();
        testOrg.setName("Test Org");
        VehicleType testVehicleType = VehicleType.CAR;

        when(organisationRepository.findByName(anyString())).thenReturn(Collections.singletonList(organisation));
        when(vehicleRepository.findByVehicleType(anyString())).thenReturn(vehicle);
        when(feeSchemaRepository.findByFeeOrgModelAndVehicleType(anyLong(), anyLong())).thenReturn(Collections.singletonList(feeSchema));
        when(parkingLotRepository.findByOrganisationAndVehicleType(any(Organisation.class), any(Vehicle.class))).thenReturn(Collections.singletonList(parkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenAnswer(new Answer<ParkingLot>() {
            @Override
            public ParkingLot answer(InvocationOnMock invocation) throws Throwable {
                ParkingLot savedParkingLot = invocation.getArgument(0);
                savedParkingLot.setId(1L); // set an ID for the saved ParkingLot
                return savedParkingLot;
            }
        });
        Map<String, String> actualData = parkingLotBookingImpl.markSpotBooking(testOrg.getName(), testVehicleType);

        assertEquals(expectedData, actualData);
    }

    @Test
    @Transactional
    void testUnMarkSpotBooking() {

        try {
            when(parkingLotRepository.findById(1L)).thenReturn(Optional.ofNullable(parkingLot));
            Thread.sleep(2000);
            Map<String, String> actualData = parkingLotBookingImpl.unMarkSpotBooking("1");
            assertEquals(actualData.get("Receipt Number"),"R-1");
            assertEquals(actualData.get("Fees"),"10");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
