package com.tawasalna.social;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
@EnableWebSocket
@CrossOrigin(origins = "*")
public class TawasalnaSocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaSocialApplication.class, args);
    }
}
