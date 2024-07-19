package com.tawasalna.facilitiesmanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.tawasalna.shared.EnableSharedModule;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
public class TawasalnaFacilitiesManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TawasalnaFacilitiesManagementApplication.class, args);
    }

}
