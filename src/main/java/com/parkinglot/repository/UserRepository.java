package com.parkinglot.repository;

import com.parkinglot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByServiceId(Long serviceId);

    boolean existsByUsername(String username);

    //boolean existsByService_Id(Long service_id);

    // boolean findByService_id(Long service_id);

    //Object findByService_id(String username);

}