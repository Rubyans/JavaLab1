package com.hrybko.JavaLab1.repositories;

import com.hrybko.JavaLab1.models.Iphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IphoneRepository extends JpaRepository<Iphone, Long> {
}
