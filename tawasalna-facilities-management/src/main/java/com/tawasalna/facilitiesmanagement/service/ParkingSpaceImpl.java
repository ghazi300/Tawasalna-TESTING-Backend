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
public class ParkingSpaceImpl implements IParkingSpace{
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
                .orElseThrow(() -> new IllegalArgumentException("ParkingLot with id " + parkingSpace.getParkingLotId().getParkinglotid() + " not found"));

        parkingSpace.setParkingLotId(parkingLot);

        ParkingSpace savedParkingSpace = parkingSpaceRepository.save(parkingSpace);

      /*  for (ParkingSubSpace subSpace : parkingSpace.getSubSpaces()) {
            if (subSpace != null) {

               subSpace.setParkingSpaceId(savedParkingSpace);
                parkingSubSpaceRepository.save(subSpace);
            }
        }*/
        // Create ParkingSubSpace instances based on capacity
        int capacity = parkingSpace.getCapacity();
        for (int i = 1; i <= capacity; i++) {
            ParkingSubSpace subSpace = new ParkingSubSpace();
            subSpace.setParkingSpaceId(savedParkingSpace);
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
        existingParkingSpace.setCapacity(parkingSpace.getCapacity());
        existingParkingSpace.setOccupiedSpaces(parkingSpace.getOccupiedSpaces());
        if (parkingSpace.getSubSpaces() != null) {
            for (ParkingSubSpace subSpace : parkingSpace.getSubSpaces()) {
                ParkingSubSpace existingSubSpace = parkingSubSpaceRepository.findById(subSpace.getSubSpaceId())
                        .orElseThrow(() -> new IllegalArgumentException("ParkingSubSpace with id " + subSpace.getSubSpaceId() + " not found"));

                existingSubSpace.setStationNumber(subSpace.getStationNumber());
                existingSubSpace.setStatus(subSpace.getStatus());
                parkingSubSpaceRepository.save(existingSubSpace);
            }
        }
        return parkingSpaceRepository.save(existingParkingSpace);
    }

    @Override
    public void deletespace(String id) {
      /*  ParkingSpace existingParkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));

          List<ParkingSubSpace> subSpaces = existingParkingSpace.getSubSpaces();

           if (subSpaces != null) {
            for (ParkingSubSpace subSpace : subSpaces) {
                 log.info(String.valueOf(subSpace));
                // Delete the ParkingSubSpace
                parkingSubSpaceRepository.delete(subSpace);
            }

            log.info("apres"+subSpaces.toString());

        }*/


       /* parkingSubSpaceRepository.deleteByParkingSpaceId(id);*/


        // Finally, delete the ParkingSpace
       // parkingSpaceRepository.delete(existingParkingSpace);


            // Find the ParkingSpace by its ID
            ParkingSpace existingParkingSpace = parkingSpaceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));

            // Retrieve associated ParkingSubSpaces
            List<ParkingSubSpace> subSpaces = parkingSpaceRepository.findSubSpacesByParkingSpaceId(id);
        System.out.println("******************");
        System.out.println(subSpaces);
        System.out.println("******************");

        // Delete associated ParkingSubSpaces if they exist
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

            // Finally, delete the ParkingSpace
            try {
                parkingSpaceRepository.delete(existingParkingSpace);
                log.info("Deleted ParkingSpace with ID: " + id);
            } catch (Exception e) {
                log.error("Error deleting ParkingSpace with ID: " + id, e);
            }


    }
}
