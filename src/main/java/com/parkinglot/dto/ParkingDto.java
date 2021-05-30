package com.parkinglot.dto;

import lombok.Data;

@Data
public class ParkingDto {

    private int parkingSeat;

    private boolean enable;

    private boolean reserved;

    private boolean reservedUser;

}
