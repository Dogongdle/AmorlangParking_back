package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.dto.ParkingDto;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;

    // a~d 섹터별 데이터 추가
    @GetMapping("/data/{sector}")
    public List<ParkingDto> showSeats(@PathVariable String sector){
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<Integer, Boolean> seats = parkingService.getSeats(parking.getId());

        List<ParkingDto> list = new ArrayList<>();
        for (Integer seatNumber : seats.keySet()) {
            ParkingDto parkingDto = new ParkingDto();
            parkingDto.setParkingSeat(seatNumber);
            parkingDto.setEnable(seats.get(seatNumber));
            list.add(parkingDto);
        }

        return list;
    }

    // 특정 자리에 주차가 되었음을 알림
    @PostMapping("/data/{sector}/{seat}")
    public ParkingDto preserveSeat(@PathVariable String sector, @PathVariable int seat){
        Parking parking = parkingRepository.findBySector(sector).get();
        boolean enable = parkingService.updateSeat(parking.getId(), seat);

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(enable);

        return parkingDto;
    }

    // 이중주차를 위한 데이터 생성
    @GetMapping("/data/{sector}/doubleSeat")
    public List<ParkingDto> showDoubleSeats(@PathVariable String sector){
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<Integer, Boolean> seats = parkingService.getDoubleSeats(parking.getId());

        List<ParkingDto> list = new ArrayList<>();
        for (Integer seatNumber : seats.keySet()) {
            ParkingDto parkingDto = new ParkingDto();
            parkingDto.setParkingSeat(seatNumber);
            parkingDto.setEnable(seats.get(seatNumber));
            list.add(parkingDto);
        }

        return list;
    }

    // 이중주차 가능 여부 설정
    @PostMapping("/data/{sector}/doubleSeat/{seat}")
    public ParkingDto preserveDoubleSeat(@PathVariable String sector, @PathVariable int seat){
        Parking parking = parkingRepository.findBySector(sector).get();
        boolean enable = parkingService.updateDoubleSeat(parking.getId(), seat);

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(enable);

        return parkingDto;
    }
}
