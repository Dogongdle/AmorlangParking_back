package com.parkinglot.controller;

import com.parkinglot.dto.FcmDto;
import com.parkinglot.fcm.FcmUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmUtil fcmUtil;

    @PostMapping("/push")
    public FcmDto push(@RequestBody FcmDto fcmDto){
        fcmUtil.sendFcm(fcmDto.getTokenId(), fcmDto.getTitle(), fcmDto.getContent());
        return fcmDto;
    }
}
