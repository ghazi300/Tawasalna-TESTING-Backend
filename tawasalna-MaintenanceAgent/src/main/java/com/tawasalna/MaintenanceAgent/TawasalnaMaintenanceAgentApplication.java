package com.tawasalna.MaintenanceAgent;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@OpenAPIDefinition
@SpringBootApplication
public class TawasalnaMaintenanceAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaMaintenanceAgentApplication.class, args);
    }

}
