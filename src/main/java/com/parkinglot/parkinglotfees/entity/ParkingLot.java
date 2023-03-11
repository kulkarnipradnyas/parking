package com.parkinglot.parkinglotfees.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "organisation_id",referencedColumnName = "id")
    private  Organisation organisation;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_type", referencedColumnName = "id")
    private Vehicle vehicleType;

    @ManyToMany
    @JoinColumn(name = "fee_schema_id")
    private List<FeeSchema> feeSchema;

    @Column(name = "entry_time", columnDefinition="DATETIME")
    private LocalDateTime entryTime;

    @Column(name = "exit_time",columnDefinition="DATETIME")
    private LocalDateTime exitTime;

    @Column(name = "parking_spot_id")
    private Integer parking_spot_number;

}
