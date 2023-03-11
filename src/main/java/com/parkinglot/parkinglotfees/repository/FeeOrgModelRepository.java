package com.parkinglot.parkinglotfees.repository;

import com.parkinglot.parkinglotfees.entity.FeeOrgModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeOrgModelRepository extends JpaRepository<FeeOrgModel,Long> {
}
