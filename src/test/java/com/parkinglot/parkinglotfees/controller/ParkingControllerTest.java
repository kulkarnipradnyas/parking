package com.parkinglot.parkinglotfees.controller;

import com.parkinglot.parkinglotfees.entity.VehicleType;
import com.parkinglot.parkinglotfees.service.ParkingLotBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ParkingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParkingLotBooking parkingLotBooking;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ParkingController(parkingLotBooking)).build();
    }

    @Test
    public void testParkVehicle() throws Exception {
        String orgName = "Stadium Parking Lot";
        VehicleType vehicleType = VehicleType.CAR;
        Map<String, String> parkingLot = new HashMap<>();
        parkingLot.put("spotNumber", "1");
        when(parkingLotBooking.markSpotBooking(any(String.class), any(VehicleType.class))).thenReturn(parkingLot);
        mockMvc.perform(post("/parking")
                        .param("orgName", orgName)
                        .param("vehicleType", vehicleType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnParkVehicle() throws Exception {
        String receiptNumber = "1";
        Map<String, String> parkingLot = new HashMap<>();
        parkingLot.put("spotNumber", "1");
        when(parkingLotBooking.unMarkSpotBooking(any(String.class))).thenReturn(parkingLot);
        mockMvc.perform(post("/unpark")
                        .param("receiptNumber", receiptNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}