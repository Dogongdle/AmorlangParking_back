package com.parkinglot.repository;

import com.parkinglot.domain.ParkingSeat;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ParkingSeatRepository {

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

    public Map<Integer, Boolean> getSeats(Long parkingId){
        ParkingSeat parkingSeat = repository.get(parkingId);
        return parkingSeat.getSeat();
    }
}
