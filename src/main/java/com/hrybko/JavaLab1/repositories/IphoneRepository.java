package com.hrybko.JavaLab1.repositories;

import com.hrybko.JavaLab1.models.IphoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IphoneRepository extends JpaRepository<IphoneModel, Long> {
}
