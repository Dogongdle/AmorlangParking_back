package com.parkinglot;

import com.parkinglot.domain.Parking;
import com.parkinglot.repository.ParkingRepository;
import com.parkinglot.repository.ParkingSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ParkingLotApplication {

	//주차장의 초기 값들을 설정하기 위한 리포지토리 선언
	@Autowired
	ParkingRepository parkingRepository;
	@Autowired
	ParkingSeatRepository parkingSeatRepository;

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
			parkingSeatRepository.generateSeat(j);
			for (int i = 1; i < 16; i++) {
				parkingSeatRepository.saveSeat(j,i,true);
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotApplication.class, args);
	}

}
