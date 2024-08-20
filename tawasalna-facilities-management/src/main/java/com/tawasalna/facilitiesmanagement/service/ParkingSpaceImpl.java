package com.tawasalna.facilitiesmanagement.service;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpaceStatus;
import com.tawasalna.facilitiesmanagement.repository.ParkingLotRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSpaceRepository;
import com.tawasalna.facilitiesmanagement.repository.ParkingSubSpaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingSpaceImpl  implements IParkingSpace   {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSubSpaceRepository parkingSubSpaceRepository;


    @Override
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }
    public List<ParkingSubSpace> getParkingSubSpaces() {
        return parkingSubSpaceRepository.findAll();
    }
    @Override
    public ParkingSpace addParkingSpace(ParkingSpace parkingSpace) {
        if (parkingSpace.getParkingLotId() == null || parkingSpace.getParkingLotId().getParkinglotid() == null) {
            throw new IllegalArgumentException("ParkingLotId must not be null");
        }

        ParkingLot parkingLot = parkingLotRepository.findById(parkingSpace.getParkingLotId().getParkinglotid())
                .orElseThrow(() -> new IllegalArgumentException("ParkingLot with id  not found"));

        parkingSpace.setParkingLotId(parkingLot);

        ParkingSpace savedParkingSpace = parkingSpaceRepository.save(parkingSpace);

        int capacity = parkingSpace.getCapacity();
        for (int i = 1; i <= capacity; i++) {
            ParkingSubSpace subSpace = new ParkingSubSpace();
            subSpace.setParkingSpaceref(savedParkingSpace);
            subSpace.setStationNumber(parkingSpace.getLocationNumber()+'.' + i);
            subSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);

            parkingSubSpaceRepository.save(subSpace);
        }

        return savedParkingSpace;
    }

    @Override

    public ParkingSpace update(String id, ParkingSpace parkingSpace) {
        ParkingSpace existingParkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));

        existingParkingSpace.setLocationNumber(parkingSpace.getLocationNumber());
        existingParkingSpace.setOccupiedSpaces(parkingSpace.getOccupiedSpaces());

        int oldCapacity = existingParkingSpace.getCapacity();
        int newCapacity = parkingSpace.getCapacity();
        existingParkingSpace.setCapacity(newCapacity);

        List<ParkingSubSpace> subSpaces = parkingSubSpaceRepository.findByParkingSpaceref(existingParkingSpace.getParkingSpaceId());

        if (subSpaces != null) {
            for (int i = 0; i < subSpaces.size(); i++) {
                ParkingSubSpace existingSubSpace = subSpaces.get(i);
                existingSubSpace.setStationNumber(parkingSpace.getLocationNumber() + '.' + (i + 1));
                parkingSubSpaceRepository.save(existingSubSpace);
            }
        }

        if (newCapacity > oldCapacity) {
            for (int i = oldCapacity + 1; i <= newCapacity; i++) {
                ParkingSubSpace newSubSpace = new ParkingSubSpace();
                newSubSpace.setParkingSpaceref(existingParkingSpace);
                newSubSpace.setStationNumber(parkingSpace.getLocationNumber() + '.' + i);
                newSubSpace.setStatus(ParkingSubSpaceStatus.AVAILABLE);
                parkingSubSpaceRepository.save(newSubSpace);
            }
        }

        return parkingSpaceRepository.save(existingParkingSpace);
    }

    @Override
    public void deletespace(String id) {
        try {
            ParkingSpace existingParkingSpace = parkingSpaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));



            List<ParkingSubSpace> subSpaces = parkingSubSpaceRepository.findByParkingSpaceref(existingParkingSpace.getParkingSpaceId());


            if (subSpaces != null && !subSpaces.isEmpty()) {
                for (ParkingSubSpace subSpace : subSpaces) {
                    try {
                        log.info("Deleting ParkingSubSpace: " + subSpace);
                        parkingSubSpaceRepository.delete(subSpace);
                    } catch (Exception e) {
                        log.error("Error deleting ParkingSubSpace: " + subSpace, e);
                    }
                }
                log.info("Deleted all associated ParkingSubSpaces for ParkingSpace ID: " + id);
            } else {
                log.info("No associated ParkingSubSpaces found for ParkingSpace ID: " + id);
            }

            // Delete the ParkingSpace
           parkingSpaceRepository.delete(existingParkingSpace);
            log.info("Deleted ParkingSpace with ID: " + id);

        } catch (Exception e) {
            log.error("Error deleting ParkingSpace with ID: " + id, e);
        }
    }




}
