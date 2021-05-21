package com.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class ParkingSeat {
    //자리 번호와 자리가 차있는지 여부
    private Map<Integer, Boolean> seat = new HashMap<>();
    //이중주차 자리 번호와 자리가 차있는지 여부
    private Map<Integer, Boolean> doubleSeat = new HashMap<>();
    //자리별로 푸시알람을 설정한 사람들의 디바이스 토큰과 플랫폼 맵
    //자리가 비었을 때 (Enable 일 때) 맵과 자리가 찼을 때 (Disable 일 때) 맵으로 구분
    private Map<Integer, Map<String, Platform>> onEnableDeviceTokens = new HashMap<>();
    private Map<Integer, Map<String, Platform>> onDisableDeviceTokens = new HashMap<>();
}
