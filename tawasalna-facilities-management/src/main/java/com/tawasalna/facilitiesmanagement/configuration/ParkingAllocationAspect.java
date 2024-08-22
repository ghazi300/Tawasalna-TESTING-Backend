package com.tawasalna.facilitiesmanagement.configuration;

import com.tawasalna.facilitiesmanagement.models.ParkingAllocationStatus;
import com.tawasalna.facilitiesmanagement.repository.ParkingAllocationRepository;
import com.tawasalna.facilitiesmanagement.service.ParkingAllocationImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class ParkingAllocationAspect {
    ParkingAllocationRepository iParkingAllocation;
    ParkingAllocationImpl parkingAllocation;
    @AfterReturning("execution(* com.tawasalna.facilitiesmanagement.controller.ParkingAllocationController.addparkingspaceallocations(..))")
    public void afterAddParkingAllocation(JoinPoint joinPoint) {
        iParkingAllocation.countByStatus(ParkingAllocationStatus.ACTIVE);
        log.info("Parking allocation added, calculating active vehicles count.");
    }
   /* @AfterReturning("execution(* com.tawasalna.facilitiesmanagement.controller.ParkingAllocationController.addparkingspaceallocations(..))")
    public void staticAllocation(JoinPoint joinPoint) {
        parkingAllocation.calculateTotalActiveVehicles();
        log.info("statistic.");
    }*/
}

