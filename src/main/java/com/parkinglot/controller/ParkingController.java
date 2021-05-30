package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.domain.User;
import com.parkinglot.dto.ParkingDto;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.UserRepository;
import com.parkinglot.service.ParkingService;
import com.parkinglot.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 주차장과 관련된 API를 작업하는 컨트롤러로,
// 데이터 추가, 데이터 변경에 대한 기능을 한다.

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final JwtTokenUtil jwtTokenUtil;
    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    // a~d 섹터별 데이터 추가
    @GetMapping("/data/{sector}")
    public List<ParkingDto> showSeats(@RequestHeader("authorization") String jwt,
                                      @PathVariable String sector){
        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);

        Parking parking = parkingRepository.findBySector(sector).get();
        Map<Integer, Boolean> seats = parkingService.getSeats(parking.getId());
        Map<Integer, LocalDateTime> reservationEndTime = parkingService.getReservationEndTime(parking.getId());
        Map<Integer, String> reservedUser = parkingService.getReservedUser(parking.getId());

        List<ParkingDto> list = new ArrayList<>();
        for (Integer seatNumber : seats.keySet()) {
            ParkingDto parkingDto = new ParkingDto();
            parkingDto.setParkingSeat(seatNumber);
            parkingDto.setEnable(seats.get(seatNumber));
            list.add(parkingDto);
        }
        //5분예약
        for (int i = 0; i< list.size(); i++){
            ParkingDto parkingDto = list.get(i);    // 리스트는 0부터
            LocalDateTime endTime = reservationEndTime.get(i+1);    // Map은 1부터 시작

            if(reservedUser.get(i+1) != null) {
                parkingDto.setReserved(endTime.isAfter(LocalDateTime.now()));
                if (reservedUser.get(i+1).equals(username)) {
                    if(endTime.isAfter(LocalDateTime.now())) {
                        parkingDto.setReservedUser(true);
                        User user = userRepository.findByUsername(username).get();
                        user.setReserved(true);
                        userRepository.save(user);
                    } else {
                        User user = userRepository.findByUsername(username).get();
                        user.setReserved(false);
                        userRepository.save(user);
                        reservedUser.remove(i+1, username);
                    }
                }
            }else {
                parkingDto.setReserved(false);
            }
        }

        return list;
    }

    // 특정 자리에 주차가 되었음을 알림
    //아두이노가 있으면 굳이 필요없음
    @PostMapping("/data/{sector}/{seat}")
    public ParkingDto preserveSeat(@PathVariable String sector, @PathVariable int seat){
        Parking parking = parkingRepository.findBySector(sector).get();
        boolean enable = parkingService.updateSeat(parking.getId(), seat);

        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(enable);
        parkingDto.setReserved(false);

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

    // 5분 예약에 대한 기능
    @PostMapping("/reserve/{sector}/{seat}")
    public ParkingDto fiveMinutes(@RequestHeader("authorization") String jwt,
                                  @PathVariable String sector,
                                  @PathVariable int seat){

        jwt = jwt.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwt);

        Parking parking = parkingRepository.findBySector(sector).get();

        parkingService.reserve(parking.getId(), username, seat);

        Map<Integer, Boolean> seats = parkingService.getSeats(parking.getId());

        ParkingDto parkingDto=new ParkingDto();
        parkingDto.setParkingSeat(seat);
        parkingDto.setEnable(seats.get(seat));
        parkingDto.setReserved(true);

        return parkingDto;
    }

}