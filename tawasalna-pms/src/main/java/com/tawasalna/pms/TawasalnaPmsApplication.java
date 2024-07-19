package com.tawasalna.pms;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
public class TawasalnaPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaPmsApplication.class, args);
    }
}