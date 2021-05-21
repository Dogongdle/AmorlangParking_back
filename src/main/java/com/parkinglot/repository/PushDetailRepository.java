package com.parkinglot.repository;

import com.parkinglot.domain.PushDetail;
import com.parkinglot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushDetailRepository extends JpaRepository<PushDetail, Long> {
    List<PushDetail> findAllByUser (User user);
}
