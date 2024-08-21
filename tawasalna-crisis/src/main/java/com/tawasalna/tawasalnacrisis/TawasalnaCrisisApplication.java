package com.tawasalna.tawasalnacrisis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@ComponentScan(basePackages = {"com.tawasalna.shared", "com.tawasalna.tawasalnacrisis"})
public class TawasalnaCrisisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaCrisisApplication.class, args);
    }

}