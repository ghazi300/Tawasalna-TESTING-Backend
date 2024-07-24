package com.tawasalna.facilitiesmanagement.controller;

import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.models.ParkingSubSpace;
import com.tawasalna.facilitiesmanagement.service.IParkingSpace;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("parkingspace")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParkingSpaceController {
    IParkingSpace iParkingSpace;
    @PostMapping("addparkingSpace")
    public ResponseEntity<ParkingSpace> addParkingLot(@RequestBody ParkingSpace parkingSpace){
        try {
            return new ResponseEntity<>(iParkingSpace.addParkingSpace(parkingSpace), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the logs
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getparkingspace")
    public ResponseEntity<List<ParkingSpace>> getParking() {
        try {
            return new ResponseEntity<>(iParkingSpace.getParkingSpaces(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getparkingSubspace")
    public ResponseEntity<List<ParkingSubSpace>> getParkingsubspace() {
        try {
            return new ResponseEntity<>(iParkingSpace.getParkingSubSpaces(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getParkingSpaceById(@PathVariable String id) {
        ParkingSpace parkingSpace = iParkingSpace.getParkingSpaces()
                .stream()
                .filter(ps -> ps.getParkingSpaceId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));
        return ResponseEntity.ok(parkingSpace);
    }

    @PutMapping("updateparkingspace/{id}")
    public ResponseEntity<ParkingSpace> updateParkingSpace(@PathVariable String id, @RequestBody ParkingSpace parkingSpace) {
        ParkingSpace updatedParkingSpace = iParkingSpace.update(id, parkingSpace);
        return ResponseEntity.ok(updatedParkingSpace);
    }

    @DeleteMapping("deleteparkingspace/{id}")
    public ResponseEntity<Void> deleteParkingSpace(@PathVariable String id) {
        iParkingSpace.deletespace(id);
        return ResponseEntity.noContent().build();
    }


}
