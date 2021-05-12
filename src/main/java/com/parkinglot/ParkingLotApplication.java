package com.parkinglot;

import com.parkinglot.domain.Parking;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired ParkingRepository parkingRepository;
	//@Autowired ParkingSeatRepository parkingSeatRepository;
	private final ParkingService parkingService;

	//메인 메서드가 돌아가기전에 초기값 설정하는 메서드
	@PostConstruct
	public void init(){
		List<Parking> parkings = Stream.of(
				new Parking(null,"a"),
				new Parking(null,"b"),
				new Parking(null,"c"),
				new Parking(null,"d")
		).collect(Collectors.toList());
		parkingRepository.saveAll(parkings);

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
	}

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotApplication.class, args);
	}

}
