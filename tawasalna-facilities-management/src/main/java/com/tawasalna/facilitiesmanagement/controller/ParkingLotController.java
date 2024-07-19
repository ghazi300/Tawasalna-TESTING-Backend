package com.tawasalna.facilitiesmanagement.controller;

import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.service.IParkingLot;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("parkinglot")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParkingLotController {
    IParkingLot iParkingLot;
    @PostMapping("addparkinglot")
    public ResponseEntity<ParkingLot> addParkingLot(@RequestBody ParkingLot parkingLot){
        try{
            return new ResponseEntity<>(iParkingLot.add(parkingLot), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getparkinglots")
    public ResponseEntity<List<ParkingLot>> getParkingLots() {
        try {
            return new ResponseEntity<>(iParkingLot.getParkingLot(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParkingLot> getParkingSpaceById(@PathVariable String id) {
        ParkingLot parkingSpace = iParkingLot.getParkingLot()
                .stream()
                .filter(ps -> ps.getParkinglotid().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ParkingSpace with id " + id + " not found"));
        return ResponseEntity.ok(parkingSpace);
    }

    @PutMapping("/updateparkinglot/{id}")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable("id") String id, @RequestBody ParkingLot parkingLot) {
        try {
            return ResponseEntity.ok(iParkingLot.update(id,parkingLot));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("deleteparkinglot/{id}")
    public ResponseEntity<HttpStatus> deleteParkingLot(@PathVariable String id) {
        try {
            iParkingLot.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
