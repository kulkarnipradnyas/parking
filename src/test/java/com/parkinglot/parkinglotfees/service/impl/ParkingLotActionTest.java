package com.parkinglot.parkinglotfees.service.impl;

import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.ParkingLot;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import com.parkinglot.parkinglotfees.repository.ParkingLotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static  org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParkingLotActionTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotAction parkingLotAction;

    @Test
    public void testSave() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        when(parkingLotRepository.save(ArgumentMatchers.any(ParkingLot.class))).thenReturn(parkingLot);

        // When
        ParkingLot result = parkingLotAction.save(parkingLot);

        // Then
        verify(parkingLotRepository, times(1)).save(ArgumentMatchers.any(ParkingLot.class));
        Assertions.assertEquals(parkingLot, result);
    }

    @Test
    public void testDelete() {
        // Given
        ParkingLot parkingLot = new ParkingLot();

        // When
        parkingLotAction.delete(parkingLot);

        // Then
        verify(parkingLotRepository, times(1)).delete(parkingLot);
    }

    @Test
    public void testFindByOrganisationAndVehicleType() {
        // Given
        Organisation organisation = new Organisation();
        Vehicle vehicleType = new Vehicle();
        List<ParkingLot> parkingLots = new ArrayList<>();
        when(parkingLotRepository.findByOrganisationAndVehicleType(organisation, vehicleType)).thenReturn(parkingLots);

        // When
        List<ParkingLot> result = parkingLotAction.findByOrganisationAndVehicleType(organisation, vehicleType);

        // Then
        verify(parkingLotRepository, times(1)).findByOrganisationAndVehicleType(organisation, vehicleType);
        Assertions.assertEquals(parkingLots, result);
    }

    @Test
    public void testFindById() {
        // Given
        Long receiptNumber = 1L;
        ParkingLot parkingLot = new ParkingLot();
        Optional<ParkingLot> optionalParkingLot = Optional.of(parkingLot);
        when(parkingLotRepository.findById(receiptNumber)).thenReturn(optionalParkingLot);

        // When
        Optional<ParkingLot> result = parkingLotAction.findById(receiptNumber);

        // Then
        verify(parkingLotRepository, times(1)).findById(receiptNumber);
        Assertions.assertEquals(optionalParkingLot, result);
    }
}