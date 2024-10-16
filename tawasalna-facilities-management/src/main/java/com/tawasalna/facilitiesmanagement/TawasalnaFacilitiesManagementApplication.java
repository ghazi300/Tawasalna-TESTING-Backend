package com.tawasalna.facilitiesmanagement;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
@EnableAspectJAutoProxy

public class TawasalnaFacilitiesManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TawasalnaFacilitiesManagementApplication.class, args);
    }

}
