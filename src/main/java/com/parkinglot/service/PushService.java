package com.parkinglot.service;

import com.parkinglot.domain.*;
import com.parkinglot.fcm.FcmUtil;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PushService {
    private final ParkingRepository parkingRepository;
    private final ParkingService parkingService;
    private final UserRepository userRepository;
    private final FcmUtil fcmUtil;

    //푸쉬를 보내기위해 푸쉬 Status 설정과 플랫폼, 디바이스토큰 초기화
    public void setPush(){
        List<User> allUser = userRepository.findAll();
        for (User user : allUser) {
            if(user.getDeviceToken() != null){
                List<PushDetail> pushDetails = user.getPushDetails();

                if(!pushDetails.isEmpty()) {
                    String deviceToken = user.getDeviceToken();
                    Platform platform = user.getPlatform();
                    PushStatus pushStatus = user.getPushStatus();

                    if (pushStatus == PushStatus.ENABLE_ONLY) {
                        for (PushDetail pushDetail : pushDetails) {
                            String sector = pushDetail.getSector();
                            Parking parking = parkingRepository.findBySector(sector).get();
                            Long parkingId = parking.getId();
                            int seat = pushDetail.getSeat();

                            parkingService.updateEnableDeviceToken(parkingId, seat, deviceToken, platform);
                        }
                    } else if (pushStatus == PushStatus.DISABLE_ONLY) {
                        for (PushDetail pushDetail : pushDetails) {
                            String sector = pushDetail.getSector();
                            Parking parking = parkingRepository.findBySector(sector).get();
                            Long parkingId = parking.getId();
                            int seat = pushDetail.getSeat();

                            parkingService.updateDisableDeviceToken(parkingId, seat, deviceToken, platform);
                        }
                    } else if (pushStatus == PushStatus.BOTH) {        //pushStatus.BOTH 일때
                        for (PushDetail pushDetail : pushDetails) {
                            String sector = pushDetail.getSector();
                            Parking parking = parkingRepository.findBySector(sector).get();
                            Long parkingId = parking.getId();
                            int seat = pushDetail.getSeat();

                            parkingService.updateEnableDeviceToken(parkingId, seat, deviceToken, platform);
                            parkingService.updateDisableDeviceToken(parkingId, seat, deviceToken, platform);
                        }
                    }
                }
            }
        }
    }

    //유저 아이디로 찾아서 푸쉬 업데이트
    public void updatePush(Long userId, PushStatus pushStatus, String sector, int seat){
        User user = userRepository.findById(userId).get();
        String deviceToken = user.getDeviceToken();
        Platform platform = user.getPlatform();

        Parking parking = parkingRepository.findBySector(sector).get();

        if (pushStatus == PushStatus.ENABLE_ONLY) {
            parkingService.updateEnableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.DISABLE_ONLY) {
            parkingService.updateDisableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.BOTH) {
            parkingService.updateEnableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.updateDisableDeviceToken(parking.getId(), seat, deviceToken, platform);
        }else {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }

    }

    //유저 아이디로 찾아서 푸쉬 삭제
    public void deletePush(Long userId, PushStatus pushStatus, String sector, int seat){
        User user = userRepository.findById(userId).get();
        String deviceToken = user.getDeviceToken();

        Parking parking = parkingRepository.findBySector(sector).get();

        if (pushStatus == PushStatus.ENABLE_ONLY) {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.DISABLE_ONLY) {
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.BOTH) {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }

    }

    //유저 이름으로 찾아서 푸쉬 업데이트
    public void updatePush(String username, PushStatus pushStatus, String sector, int seat){
        User user = userRepository.findByUsername(username).get();
        String deviceToken = user.getDeviceToken();
        Platform platform = user.getPlatform();

        Parking parking = parkingRepository.findBySector(sector).get();

        if (pushStatus == PushStatus.ENABLE_ONLY) {
            parkingService.updateEnableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.DISABLE_ONLY) {
            parkingService.updateDisableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.BOTH) {
            parkingService.updateEnableDeviceToken(parking.getId(), seat, deviceToken, platform);
            parkingService.updateDisableDeviceToken(parking.getId(), seat, deviceToken, platform);
        }else {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }

    }

    //유저 이름으로 찾아서 푸쉬 삭제
    public void deletePush(String username, PushStatus pushStatus, String sector, int seat){
        User user = userRepository.findByUsername(username).get();
        String deviceToken = user.getDeviceToken();

        Parking parking = parkingRepository.findBySector(sector).get();

        if (pushStatus == PushStatus.ENABLE_ONLY) {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.DISABLE_ONLY) {
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }else if (pushStatus == PushStatus.BOTH) {
            parkingService.deleteEnableDeviceToken(parking.getId(), seat, deviceToken);
            parkingService.deleteDisableDeviceToken(parking.getId(), seat, deviceToken);
        }

    }

    //자리가 비었을 때 (enable) 푸쉬보내기
    public void sendEnablePush(String sector, int seat){
        String message = sector + "섹터의 " + seat + "번 자리가 비었습니다.";
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<String, Platform> deviceTokenMap = parkingService.getEnableDeviceTokenMap(parking.getId(), seat);

        for (String deviceToken : deviceTokenMap.keySet()) {
            Platform platform = deviceTokenMap.get(deviceToken);
            fcmUtil.sendFcm(deviceToken, "아몰랑파킹 주차알림", message, platform);
        }
    }

    //자리가 찼을 떄 (disable) 푸쉬보내기
    public void sendDisablePush(String sector, int seat){
        String message = sector + "섹터의 " + seat + "번 자리가 찼습니다.";
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<String, Platform> deviceTokenMap = parkingService.getDisableDeviceTokenMap(parking.getId(), seat);

        for (String deviceToken : deviceTokenMap.keySet()) {
            Platform platform = deviceTokenMap.get(deviceToken);
            fcmUtil.sendFcm(deviceToken, "아몰랑파킹 주차알림", message, platform);
        }
    }
}
