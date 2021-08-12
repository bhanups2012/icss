package com.ics.service.repository;

import com.ics.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeRateRepository extends JpaRepository<Rate, Integer> {
    Rate findByRateId(int rateId);
}
