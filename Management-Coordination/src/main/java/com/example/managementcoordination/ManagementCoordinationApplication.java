package com.example.managementcoordination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.tawasalna.shared", "com.example.managementcoordination"})

@SpringBootApplication
public class ManagementCoordinationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementCoordinationApplication.class, args);
    }

}
