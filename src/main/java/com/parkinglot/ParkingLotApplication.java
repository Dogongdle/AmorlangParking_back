package com.parkinglot;

import com.parkinglot.domain.Parking;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.service.ParkingService;

import com.parkinglot.service.PushService;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class ParkingLotApplication {

	//주차장의 초기 값들을 설정하기 위한 리포지토리 선언

	private final ParkingService parkingService;
	private final PushService pushService;
	
	//메인 메서드가 돌아가기전에 초기값 설정하는 메서드
	@PostConstruct
	public void init(){
		//처음 시작할때 딱한번만 실행하는 로직. 두번째 실행부터 이 로직이 실행되면 나중에 주차장정보 받아올때
		//디비에 계속 쌓이면서 에러가 터진다.
		/*
		List<Parking> parkings = Stream.of(
				new Parking(null,"a"),
				new Parking(null,"b"),
				new Parking(null,"c"),
				new Parking(null,"d")
		).collect(Collectors.toList());
		parkingRepository.saveAll(parkings);
		*/


		for(Long j = 1L; j<5L; j++) {
			parkingService.generateSeat(j);
			for (int i = 1; i < 16; i++) {
				parkingService.saveSeat(j,i,true);

      }
			for (int i = 1; i < 6; i++) {

				parkingService.saveDoubleSeat(j,i,true);
			}
			parkingService.updateSeat(j,1);
			parkingService.updateSeat(j,2);
			parkingService.updateSeat(j,3);
			parkingService.updateSeat(j,7);
			parkingService.updateSeat(j,9);
			parkingService.updateSeat(j,12);
			parkingService.updateSeat(j,13);
			parkingService.updateSeat(j,15);

			parkingService.updateDoubleSeat(j,1);
			parkingService.updateDoubleSeat(j,2);
			parkingService.updateDoubleSeat(j,3);

		}


		//서버 시작할때 푸쉬 한번에 설정하기
		pushService.setPush();


	}

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotApplication.class, args);
	}

}
