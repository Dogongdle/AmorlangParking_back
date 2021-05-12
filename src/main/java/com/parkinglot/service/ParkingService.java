package com.parkinglot.service;

import com.parkinglot.domain.ParkingSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final Map<Long, ParkingSeat> repository = new HashMap<>();

    public void generateSeat(Long parkingId){
        repository.put(parkingId,new ParkingSeat());
    }

    public void saveSeat(Long parkingId, int seatNumber, boolean bool){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> seat = parkingSeat.getSeat();
        seat.put(seatNumber, bool);
    }

    public boolean updateSeat(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> seat = parkingSeat.getSeat();
        seat.put(seatNumber, !seat.get(seatNumber));
        return seat.get(seatNumber);
    }

    public Map<Integer, Boolean> getSeats(Long parkingId) {
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getSeat();
    }

    public void saveDoubleSeat(Long parkingId, int seatNumber, boolean bool){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> doubleSeat = parkingSeat.getDoubleSeat();
        doubleSeat.put(seatNumber, bool);
    }

    public boolean updateDoubleSeat(Long parkingId, int seatNumber){
        ParkingSeat parkingSeat = repository.get(parkingId);
        Map<Integer, Boolean> doubleSeat = parkingSeat.getDoubleSeat();
        doubleSeat.put(seatNumber, !doubleSeat.get(seatNumber));
        return doubleSeat.get(seatNumber);
    }

    public Map<Integer, Boolean> getDoubleSeats(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getDoubleSeat();
    }
}

