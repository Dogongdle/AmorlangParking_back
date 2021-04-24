package com.parkinglot.profile;

import com.parkinglot.get.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Profile {

    private String apart;

    private String phoneNumber;

    private String carNumber;

    private String email;

    private String bio;

}
