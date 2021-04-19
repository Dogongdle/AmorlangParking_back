package com.parkinglot.get;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

// json 내부 정보를 감싸기 위한 dto 설정

@Getter
@Setter
@Builder
public class UserDto {

    private Long serviceId;

    @Column(unique = true)
    private String username;

    private String password;

    private String provider;

    private String token;
}