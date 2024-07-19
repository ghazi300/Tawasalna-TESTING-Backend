package com.tawasalna.administration;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@OpenAPIDefinition
@CrossOrigin(origins = "*")
@EnableSharedModule
@EnableScheduling
public class TawasalnaAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaAdministrationApplication.class, args);
    }
}
