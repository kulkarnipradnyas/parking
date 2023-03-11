package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.FeeOrgModel;
import com.parkinglot.parkinglotfees.entity.FeeSchema;
import com.parkinglot.parkinglotfees.entity.Vehicle;
import com.parkinglot.parkinglotfees.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeSchemaRepository extends JpaRepository<FeeSchema,Long> {

    @Query("SELECT fs FROM FeeSchema fs JOIN fs.feeOrgModel fm JOIN fs.vehicleType v WHERE fm.id = :feeOrgModelId AND v.id = :vehicleId")
    public List<FeeSchema> findByFeeOrgModelAndVehicleType(Long feeOrgModelId, Long vehicleId);

    void findAllById(Long id);
}
