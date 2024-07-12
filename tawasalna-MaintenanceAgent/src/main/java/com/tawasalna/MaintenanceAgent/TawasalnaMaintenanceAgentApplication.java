package com.tawasalna.MaintenanceAgent;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@EnableSharedModule
@SpringBootApplication
public class TawasalnaMaintenanceAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaMaintenanceAgentApplication.class, args);
    }

}
