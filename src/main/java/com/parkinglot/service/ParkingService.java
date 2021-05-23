package com.parkinglot.service;

import com.parkinglot.domain.ParkingSeat;
import com.parkinglot.domain.Platform;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParkingService {

    private final Map<Long, ParkingSeat> repository = new HashMap<>();

    //---------------------[ 시작 ] 주차 자리 관련 메소드들---------------------
    //주차장 섹터별 주차자리를 저장하는 맵 초기화 메소드
    public void generateSeat(Long parkingId){
        repository.put(parkingId,new ParkingSeat());
    }

    //주차자리 초기화 메소드
    public void saveSeat(Long parkingId, int seatNumber, boolean bool){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> seat = parkingSeat.getSeat();
        seat.put(seatNumber, bool);
        Map<Integer, LocalDateTime> reservationEndTime = parkingSeat.getReservationEndTime();
        reservationEndTime.put(seatNumber, LocalDateTime.now());
        Map<Integer, String> reservedUser = parkingSeat.getReservedUser();
        reservedUser.put(seatNumber, null);

        //디바이스 토큰을 위한 맵 초기화 하기
        //주차자리마다 디바이스 토큰이 필요하므로(일대일매칭) 주차자리를 초기화할때 같이 초기화한다.
        Map<String, Platform> enableDeviceToken = new HashMap<>();
        Map<String, Platform> disableDeviceToken = new HashMap<>();
        Map<Integer, Map<String, Platform>> onEnableDeviceTokenMap = parkingSeat.getOnEnableDeviceTokens();
        Map<Integer, Map<String, Platform>> onDisableDeviceTokenMap = parkingSeat.getOnDisableDeviceTokens();
        onEnableDeviceTokenMap.put(seatNumber, enableDeviceToken);
        onDisableDeviceTokenMap.put(seatNumber, disableDeviceToken);
    }

    //주차자리 상태 변경 메소드
    public boolean updateSeat(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> seat = parkingSeat.getSeat();
        seat.put(seatNumber, !seat.get(seatNumber));
        return seat.get(seatNumber);
    }

    //주차자리 반환 메소드
    public Map<Integer, Boolean> getSeats(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getSeat();
    }

    //이중주차자리 초기화 메소드
    public void saveDoubleSeat(Long parkingId, int seatNumber, boolean bool){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> doubleSeat = parkingSeat.getDoubleSeat();
        doubleSeat.put(seatNumber, bool);
    }

    //이중주차자리 상태 변경 메소드
    public boolean updateDoubleSeat(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> doubleSeat = parkingSeat.getDoubleSeat();
        doubleSeat.put(seatNumber, !doubleSeat.get(seatNumber));
        return doubleSeat.get(seatNumber);
    }

    //이중주차자리 반환 메소드
    public Map<Integer, Boolean> getDoubleSeats(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getDoubleSeat();
    }
    //---------------------[ 끝 ] 주차 자리 관련 메소드들---------------------

    //---------------------[ 시작 ] 푸쉬를위한 디바이스 토큰 메소드들---------------------
    //주차자리가 비었을 때(Enable) 디바이스 토큰 할당 메소드
    public void updateEnableDeviceToken(Long parkingId, int seatNumber,
                                  String deviceToken, Platform platform){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnEnableDeviceTokens();
        deviceTokenMap.get(seatNumber).put(deviceToken, platform);
    }

    //주차자리가 비었을 때(Enable) 디바이스 토큰 삭제 메소드
    public void deleteEnableDeviceToken(Long parkingId, int seatNumber,
                                  String deviceToken){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnEnableDeviceTokens();
        deviceTokenMap.get(seatNumber).remove(deviceToken);
    }

    //주차자리가 비었을 때(Enable) 알림을 보낼 디바이스 토큰들을 저장한 맵 반환 메소드
    public Map<String, Platform> getEnableDeviceTokenMap(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnEnableDeviceTokens();
        return deviceTokenMap.get(seatNumber);
    }

    //주차자리가 찼을 때(Disable) 디바이스 토큰 할당 메소드
    public void updateDisableDeviceToken(Long parkingId, int seatNumber,
                                        String deviceToken, Platform platform){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnDisableDeviceTokens();
        deviceTokenMap.get(seatNumber).put(deviceToken, platform);
    }

    //주차자리가 찼을 때(Disable) 디바이스 토큰 삭제 메소드
    public void deleteDisableDeviceToken(Long parkingId, int seatNumber,
                                        String deviceToken){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnDisableDeviceTokens();
        deviceTokenMap.get(seatNumber).remove(deviceToken);
    }

    //주차자리가 찼을 때(Disable) 알림을 보낼 디바이스 토큰들을 저장한 맵 반환 메소드
    public Map<String, Platform> getDisableDeviceTokenMap(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Map<String, Platform>> deviceTokenMap = parkingSeat.getOnDisableDeviceTokens();
        return deviceTokenMap.get(seatNumber);
    }
    //---------------------[ 끝 ] 푸쉬를위한 디바이스 토큰 메소드들---------------------

    public void reserve(Long parkingId,String username, int seatNumber){
        ParkingSeat parkingSeat=repository.get(parkingId);
        Map<Integer, LocalDateTime> reservationEndTime = parkingSeat.getReservationEndTime();
        reservationEndTime.put(seatNumber,LocalDateTime.now().plusMinutes(5));
        Map<Integer, String> reservedUser = parkingSeat.getReservedUser();
        reservedUser.put(seatNumber, username);
    }

    public Map<Integer, LocalDateTime> getReservationEndTime(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getReservationEndTime();
    }

    public Map<Integer, String> getReservedUser(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getReservedUser();
    }
}
