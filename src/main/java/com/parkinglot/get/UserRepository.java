package com.parkinglot.get;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByServiceId(Long serviceId);

    Boolean existsByServiceId(Long serviceId);

}