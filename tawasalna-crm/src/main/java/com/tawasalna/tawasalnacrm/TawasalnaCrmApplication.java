package com.tawasalna.tawasalnacrm;

import com.tawasalna.shared.EnableSharedModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
@EnableSharedModule
public class TawasalnaCrmApplication {
	public static void main(String[] args) {
		SpringApplication.run(TawasalnaCrmApplication.class, args);
	}
}
