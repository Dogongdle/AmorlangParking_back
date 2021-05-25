package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.dto.ArduinoDto;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.service.ParkingService;
import com.parkinglot.service.PushService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ArduinoController {

    private final PushService pushService;
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

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

    @PostMapping("/parking-double")
    public String updateParkingDoubleSeat(@RequestBody ArduinoDto arduinoDto){
        String sector = arduinoDto.getSector();
        int seat = arduinoDto.getNumber();
        boolean enable = arduinoDto.isEnable();

        Parking parking = parkingRepository.findBySector(sector).get();
        Long parkingId = parking.getId();

        Map<Integer, Boolean> seatMap = parkingService.getDoubleSeats(parkingId);
        if(seatMap.get(seat) != enable) {
            if (enable) {
                pushService.sendEnablePush(sector, seat);
            } else {
                pushService.sendDisablePush(sector, seat);
            }
            parkingService.updateDoubleSeat(parkingId, seat);
            return "Changed status";
        }

        return "Not changed status";
    }
}