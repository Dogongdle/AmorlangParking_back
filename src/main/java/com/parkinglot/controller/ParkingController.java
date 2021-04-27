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

/*    @GetMapping("/data/a")
    public ResponseEntity<List<ParkingDto>> generateDataA(){
        List<ParkingDto> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            ParkingDto parking=new ParkingDto();
            parking.setParkingSeat(i);
            if (i%3==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/data/b")
    public ResponseEntity<List<ParkingDto>> generateDataB(){
        List<ParkingDto> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            ParkingDto parking=new ParkingDto();
            parking.setParkingSeat(i);
            if (i%4==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/data/c")
    public ResponseEntity<List<ParkingDto>> generateDataC(){
        List<ParkingDto> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            ParkingDto parking=new ParkingDto();
            parking.setParkingSeat(i);
            if (i%5==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/data/d")
    public ResponseEntity<List<ParkingDto>> generateDataD(){
        List<ParkingDto> list=new ArrayList<>();
        for(int i=1;i<16;i++){
            ParkingDto parking=new ParkingDto();
            parking.setParkingSeat(i);
            if (i%6==0) parking.setEnable(true);
            else parking.setEnable(false);
            list.add(parking);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }*/

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
}
