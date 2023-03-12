package com.parkinglot.parkinglotfees.model;

import com.parkinglot.parkinglotfees.entity.FeeSchema;
import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ParkedResponse {

    private Long id;
    private Organisation organisation;

    private Vehicle vehicleType;

    private List<FeeSchema> feeSchema;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Integer parking_spot_number;

}