package com.example.residentsupportservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@ComponentScan(basePackages = {"com.tawasalna.shared", "com.example.residentsupportservices"})
public class ResidentSupportServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResidentSupportServicesApplication.class, args);
    }

}
