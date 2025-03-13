package com.hrybko.JavaLab1.repositories;

import com.hrybko.JavaLab1.models.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateModel, Long> {
    ExchangeRateModel findTopByOrderByIdDesc();
}
