package com.parkinglot.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

// 일종의 DTO, rare한 정보대신 가공된 정보를 받을 수 있도록 함

@Getter @Setter
@NoArgsConstructor //need default constructor for JSON Parsing
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private Long serviceId;
    private String username;
    private String provider;
    private String token;
    //private String password;
}