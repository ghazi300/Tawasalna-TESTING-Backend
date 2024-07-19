package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingLotImpl implements  IParkingLot{
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public List<ParkingLot> getParkingLot() {
        return parkingLotRepository.findAll();
    }

    @Override
    public ParkingLot add(ParkingLot parkingLot) {
        return  parkingLotRepository.save(parkingLot);
    }

    @Override
    public ParkingLot update(String id, ParkingLot parkingLot) {

        Optional< ParkingLot > parkingLot1 = Optional.ofNullable(parkingLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(parkingLot.getParkinglotid())));
        if (parkingLot1.isPresent()) {
            ParkingLot parkingLot1Update = parkingLot1.get();
            parkingLot1Update.setName(parkingLot.getName());
            parkingLot1Update.setLoacation(parkingLot.getLoacation());
            parkingLot1Update.setTotalspace(parkingLot.getTotalspace());


            return parkingLotRepository.save(parkingLot1Update);
        } else {
            throw new RuntimeException("Record not found ");
        }
    }

    @Override
    public void delete(String id) {
        parkingLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id));
        parkingLotRepository.deleteById(id);
    }
}
