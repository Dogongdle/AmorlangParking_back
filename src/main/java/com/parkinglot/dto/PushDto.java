package com.parkinglot.dto;

import com.parkinglot.domain.PushStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PushDto {

    private String sector;
    private int seat;
}
