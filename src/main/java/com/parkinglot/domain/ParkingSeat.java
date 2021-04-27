package com.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ParkingSeat {
    //자리 번호와 자리가 차있는지 여부
    private Map<Integer, Boolean> seat = new HashMap<>();
}
