package com.tawasalna.auth;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
@EnableScheduling
public class TawasalnaAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaAuthenticationApplication.class, args);
    }
}
