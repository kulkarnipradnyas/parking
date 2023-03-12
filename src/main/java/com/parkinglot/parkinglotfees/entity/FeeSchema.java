package com.parkinglot.parkinglotfees.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeeSchema)) return false;

        FeeSchema feeSchema = (FeeSchema) o;

        if (!id.equals(feeSchema.id)) return false;
        if (!feeOrgModel.equals(feeSchema.feeOrgModel)) return false;
        if (!interval.equals(feeSchema.interval)) return false;
        if (!fees.equals(feeSchema.fees)) return false;
        return vehicleType.equals(feeSchema.vehicleType);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + feeOrgModel.hashCode();
        result = 31 * result + interval.hashCode();
        result = 31 * result + fees.hashCode();
        result = 31 * result + vehicleType.hashCode();
        return result;
    }

}
