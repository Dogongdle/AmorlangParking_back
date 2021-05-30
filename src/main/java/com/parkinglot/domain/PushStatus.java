package com.parkinglot.domain;

public enum PushStatus {
    ENABLE_ONLY, // 자리가 비었을 때 ( 주차가 가능해 졌을 때 )
    DISABLE_ONLY, // 자리가 찼을 때 ( 주차가 불가능해 졌을 때 )
    BOTH, // 자리가 비었을 때 & 자리가 찼을 때
    NEITHER // 아무런 알림이 없을 때
}
