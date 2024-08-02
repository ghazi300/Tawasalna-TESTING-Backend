package com.tawasalna.facilitiesmanagement.controller;

import com.tawasalna.facilitiesmanagement.models.ParkingViolation;
import com.tawasalna.facilitiesmanagement.service.IParkingViolation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("parkingviolation")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParkingViolationController {
    IParkingViolation iParkingViolation;

    @GetMapping("getviolation")
    public ResponseEntity<List<ParkingViolation>> generateParkingViolationsEndpoint() {
        try {
            List<ParkingViolation> violations = iParkingViolation.generateParkingViolations();
            return new ResponseEntity<>(violations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
