package com.tawasalnasecuritysafety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@ComponentScan(basePackages = {"com.tawasalna.shared", "com.tawasalnasecuritysafety"})
public class TawasalnaSecuritySafetyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaSecuritySafetyApplication.class, args);
    }

}
