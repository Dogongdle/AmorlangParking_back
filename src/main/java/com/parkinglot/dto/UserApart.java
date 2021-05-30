package com.parkinglot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserApart {

    private String username;
    private String apart;
    private String provider;
    private boolean reserved;
}
