package com.tawasalna.facilitiesmanagement.controller;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocation;
import com.tawasalna.facilitiesmanagement.models.ParkingLot;
import com.tawasalna.facilitiesmanagement.models.ParkingSpace;
import com.tawasalna.facilitiesmanagement.service.IParkingAllocation;
import com.tawasalna.facilitiesmanagement.service.IParkingSpace;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("parkingAllocation")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParkingAllocationController {

    IParkingAllocation iParkingAllocation;
    @PostMapping("addparkingspaceallocations")
    public ResponseEntity<ParkingAllocation> addparkingspaceallocations(@RequestBody ParkingAllocation parkingAllocation){
        try {
            return new ResponseEntity<>(iParkingAllocation.add(parkingAllocation), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the logs
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getparkingAllocations")
    public ResponseEntity<List<ParkingAllocation>> getParkingallocations() {
        try {
            return new ResponseEntity<>(iParkingAllocation.getParkingAllocations(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
