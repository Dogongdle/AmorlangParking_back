package com.parkinglot.controller;

import com.parkinglot.domain.PushDetail;
import com.parkinglot.domain.PushStatus;
import com.parkinglot.domain.User;
import com.parkinglot.dto.PushDto;
import com.parkinglot.fcm.FcmUtil;
import com.parkinglot.repository.PushDetailRepository;
import com.parkinglot.repository.UserRepository;
import com.parkinglot.service.PushService;
import com.parkinglot.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PushController {

    private final UserRepository userRepository;
    private final PushDetailRepository pushDetailRepository;
    private final PushService pushService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/push/status")
    public Map<String, PushStatus> pushStatus(@RequestHeader("authorization") String jwt){

        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username).get();
        PushStatus pushStatus = user.getPushStatus();
        Map<String, PushStatus> send = new HashMap<>();
        send.put("pushStatus", pushStatus);

        return send;
    }

    @PostMapping("/push/status")
    public Map<String, PushStatus> updateStatus(@RequestHeader("authorization") String jwt,
                                                @RequestBody Map<String, PushStatus> status){
        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username).get();
        Long userId = user.getId();
        PushStatus pushStatus = status.get("pushStatus");
        log.info(status.get("pushStatus").toString());
        log.info(pushStatus.toString());

        List<PushDetail> pushDetails = user.getPushDetails();
        for (PushDetail pushDetail : pushDetails) {
            String sector = pushDetail.getSector();
            int seat = pushDetail.getSeat();
            pushService.updatePush(userId, pushStatus, sector, seat);
        }
        user.setPushStatus(pushStatus);
        userRepository.save(user);

        return status;
    }

    @GetMapping("/push")
    public List<PushDto> pushDetails(@RequestHeader("authorization") String jwt){

        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username).get();
        List<PushDetail> pushDetails = user.getPushDetails();
        List<PushDto> pushDtoList = new ArrayList<>();
        for (PushDetail pushDetail : pushDetails) {
            PushDto pushDto = new PushDto();
            pushDto.setSector(pushDetail.getSector());
            pushDto.setSeat(pushDetail.getSeat());
            pushDtoList.add(pushDto);
        }

        return pushDtoList;
    }

    @PostMapping("/push")
    public PushDto updateDetail(@RequestHeader("authorization") String jwt,
                                @RequestBody PushDto pushDto){
        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username).get();

        //현재 유저가 같은 알람을 등록했는지 확인하고 익셉션을 날리는 부분
        List<PushDetail> allByUser = pushDetailRepository.findAllByUser(user);
        for (PushDetail pushDetail : allByUser) {
            if(pushDetail.getSector() == pushDto.getSector()){
                if(pushDetail.getSeat() == pushDto.getSeat()){
                    throw new IllegalStateException("중복된 좌석에 알람을 등록했습니다.");
                }
            }
        }

        PushDetail newPush = new PushDetail();
        newPush.setSector(pushDto.getSector());
        newPush.setSeat(pushDto.getSeat());
        newPush.setUser(user);
        pushService.updatePush(user.getId(), user.getPushStatus(), newPush.getSector(), newPush.getSeat());
        pushDetailRepository.save(newPush);

        return pushDto;
    }

    @PostMapping("/push/delete")
    public PushDto deleteDetail(@RequestHeader("authorization") String jwt,
                                @RequestBody PushDto pushDto){
        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);
        User user = userRepository.findByUsername(username).get();

        //현재 유저가 등록한 알람중에 있으면 삭제
        //없으면 익셉션을 날리는 부분
        List<PushDetail> allByUser = pushDetailRepository.findAllByUser(user);
        for (PushDetail pushDetail : allByUser) {
            if(pushDetail.getSector() == pushDto.getSector()){
                if(pushDetail.getSeat() == pushDto.getSeat()){
                    pushService.deletePush(user.getId(), user.getPushStatus(), pushDto.getSector(), pushDto.getSeat());
                    pushDetailRepository.delete(pushDetail);
                    return pushDto;
                }
            }
        }
        throw new IllegalStateException("삭제하려는 알람이 등록되어있지 않습니다.");
    }

}
