package com.parkinglot.parkinglotfees.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="organisation")
public class Organisation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private  String name;

    @Column(name = "motor_cycle_seat")
    private Integer motorCycleSeat;

    @Column(name = "car_seat")
    private Integer carSeat;

    @Column(name = "bus_seat")
    private Integer busSeat;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "fee_org_id")
    private FeeOrgModel feeOrgModels;

}
