package com.parkinglot.get;

import lombok.*;

import javax.persistence.Column;

// json 내부 정보를 감싸기 위한 dto 설정

@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private Long serviceId;

    @Column(unique = true)
    private String username;

    private String password;

    private String provider;

    private String apart;

    private String token;


}