package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.FeeOrgModel;
import com.parkinglot.parkinglotfees.entity.Organisation;
import com.parkinglot.parkinglotfees.entity.ParkingLot;
import com.parkinglot.parkinglotfees.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {

    List<Organisation> findByName(String orgName);

}
