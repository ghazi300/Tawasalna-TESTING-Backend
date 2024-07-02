package com.tawasalna.business;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
@EnableScheduling
public class TawasalnaBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaBusinessApplication.class, args);
    }
}