package com.ipactconsult.tawasalnabackendapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TawasalnaCommunityEngagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawasalnaCommunityEngagementApplication.class, args);
    }

}
