package com.parkinglot.repository;

import com.parkinglot.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Transactional(readOnly = true)
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findBySector(String sector);
}
