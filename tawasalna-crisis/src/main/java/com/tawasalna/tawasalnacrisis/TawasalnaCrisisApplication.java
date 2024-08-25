package com.tawasalna.tawasalnacrisis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import com.tawasalna.shared.EnableSharedModule;
@SpringBootApplication
@EnableMongoAuditing
@EnableSharedModule
public class TawasalnaCrisisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaCrisisApplication.class, args);
    }

}