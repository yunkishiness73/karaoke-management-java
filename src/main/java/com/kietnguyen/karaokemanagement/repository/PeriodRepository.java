package com.kietnguyen.karaokemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kietnguyen.karaokemanagement.model.Period;

public interface PeriodRepository extends JpaRepository<Period, Integer>, JpaSpecificationExecutor<Period> {

}
