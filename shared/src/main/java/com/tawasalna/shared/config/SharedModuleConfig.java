package com.tawasalna.shared.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@NoArgsConstructor
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.tawasalna.shared"})
public class SharedModuleConfig {
}
