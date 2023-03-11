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
@Table(name="fee_schema")
public class FeeSchema {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fee_org_id", referencedColumnName = "id")
    private FeeOrgModel feeOrgModel;

    private String interval;

    private Integer fees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type", referencedColumnName = "id")
    private Vehicle vehicleType;

}
