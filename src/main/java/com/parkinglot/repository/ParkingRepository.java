package com.parkinglot.repository;

import com.parkinglot.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findBySector(String sector);
}
