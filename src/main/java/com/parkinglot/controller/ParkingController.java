package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.dto.ParkingDto;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.ParkingSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ParkingRepository parkingRepository;
    private final ParkingSeatRepository parkingSeatRepository;


    @GetMapping("/data/{sector}")
    public List<ParkingDto> showSeats(@PathVariable String sector){
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<Integer, Boolean> seats = parkingSeatRepository.getSeats(parking.getId());

        List<ParkingDto> list = new ArrayList<>();
        for (Integer seatNumber : seats.keySet()) {
            ParkingDto parkingDto = new ParkingDto();
            parkingDto.setParkingSeat(seatNumber);
            parkingDto.setEnable(seats.get(seatNumber));
            list.add(parkingDto);
        }

        return list;
    }

    @PostMapping("/data/{sector}/{seat}")
    public ParkingDto preserveSeat(@PathVariable String sector, @PathVariable int seat){
        Parking parking = parkingRepository.findBySector(sector).get();
        boolean enable = parkingSeatRepository.updateSeat(parking.getId(), seat);

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(enable);

        return parkingDto;
    }

    @GetMapping("/data/{sector}/double")
    public List<ParkingDto> showDoubleSeats(@PathVariable String sector){
        Parking parking = parkingRepository.findBySector(sector).get();
        Map<Integer, Boolean> seats = parkingSeatRepository.getDoubleSeats(parking.getId());

        List<ParkingDto> list = new ArrayList<>();
        for (Integer seatNumber : seats.keySet()) {
            ParkingDto parkingDto = new ParkingDto();
            parkingDto.setParkingSeat(seatNumber);
            parkingDto.setEnable(seats.get(seatNumber));
            list.add(parkingDto);
        }

        return list;
    }

    @PostMapping("/data/{sector}/double/{seat}")
    public ParkingDto preserveDoubleSeat(@PathVariable String sector, @PathVariable int seat){
        Parking parking = parkingRepository.findBySector(sector).get();
        boolean enable = parkingSeatRepository.updateDoubleSeat(parking.getId(), seat);

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(enable);

        return parkingDto;
    }


}
