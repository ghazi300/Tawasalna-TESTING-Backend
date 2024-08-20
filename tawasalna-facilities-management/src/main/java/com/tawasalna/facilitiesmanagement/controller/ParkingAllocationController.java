package com.tawasalna.facilitiesmanagement.controller;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.service.IParkingAllocation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("parkingAllocation")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParkingAllocationController {

  private  final  IParkingAllocation iParkingAllocation;

    @PostMapping("addparkingspaceallocations")
    public ResponseEntity<ParkingAllocation> addparkingspaceallocations(@RequestBody ParkingAllocation parkingAllocation){
        try {
            return new ResponseEntity<>(iParkingAllocation.add(parkingAllocation), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the logs
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getparkingAllocations")
    public ResponseEntity<List<ParkingAllocation>> getParkingallocations() {
        try {
            return new ResponseEntity<>(iParkingAllocation.getParkingAllocations(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("updateendtime")
    public ResponseEntity<ParkingAllocation> updateEndTime( @RequestBody ParkingAllocation p) {

        try {
            return ResponseEntity.ok(iParkingAllocation.updateEndTime(p));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("deleteparkingallocation/{id}")
    public ResponseEntity<HttpStatus> deleteParkingLot(@PathVariable String id) {
        try {
            iParkingAllocation.deleteAllocation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("addfine")
    public ResponseEntity<ParkingAllocation> addfine( @RequestBody ParkingAllocation p) {
        try {
            return ResponseEntity.ok(iParkingAllocation.addfine(p));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("violations")
    public ResponseEntity<List<ParkingAllocation>> getViolationParking() {
        try {
            return new ResponseEntity<>(iParkingAllocation.getViolationParking(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("activeVehicleCount")
    public ResponseEntity<Long> getActiveVehicleCount() {
        try {
            return new ResponseEntity<>(iParkingAllocation.calculateTotalActiveVehicles(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getaa")
    public ResponseEntity<Boolean> getaa() {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
