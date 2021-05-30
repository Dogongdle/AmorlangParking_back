package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.dto.ArduinoDto;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.service.ParkingService;
import com.parkinglot.service.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Arduino Sensor와의 연동을 하는 작업으로, Arduino로부터 온 json Type의 데이터를
// 가공하여 이를 Parking의 데이터와 연동한 후 프론트로 보내는 작업을 한다.

@RestController
@RequiredArgsConstructor
public class ArduinoController {

    private final PushService pushService;
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

    /* Arduino json data :
       sector : a~d, seat : 1~15, enable : True/False
       이를 메모리의 일반주차 자리의 데이터에 업데이트한다.
       자리의 enable이 바뀌었을 경우 push Message를 보내준다.
     */
    @PostMapping("/parking")
    public String updateParkingSeat(@RequestBody ArduinoDto arduinoDto){
        String sector = arduinoDto.getSector();
        int seat = arduinoDto.getNumber();
        boolean enable = arduinoDto.isEnable();

        Parking parking = parkingRepository.findBySector(sector).get();
        Long parkingId = parking.getId();

        Map<Integer, Boolean> seatMap = parkingService.getSeats(parkingId);
        if(seatMap.get(seat) != enable) {
            if (enable) {
                pushService.sendEnablePush(sector, seat);
            } else {
                pushService.sendDisablePush(sector, seat);
            }
            parkingService.updateSeat(parkingId, seat);
            return "Changed status";
        }

        return "Not changed status";
    }
    /*
        sector : a~d, seat : 1~5, enable : True/False
        이중주차 자리의 데이터를 정제한 뒤, 변경여부를 FrontEnd에 보내준다.
        이 경우에는 PushMessage를 보내지 않는다.
     */

    @PostMapping("/parking-double")
    public String updateParkingDoubleSeat(@RequestBody ArduinoDto arduinoDto){
        String sector = arduinoDto.getSector();
        int seat = arduinoDto.getNumber();
        boolean enable = arduinoDto.isEnable();

        Parking parking = parkingRepository.findBySector(sector).get();
        Long parkingId = parking.getId();

        Map<Integer, Boolean> seatMap = parkingService.getDoubleSeats(parkingId);
        if(seatMap.get(seat) != enable) {
            parkingService.updateDoubleSeat(parkingId, seat);
            return "Changed status";
        }

        return "Not changed status";
    }
}