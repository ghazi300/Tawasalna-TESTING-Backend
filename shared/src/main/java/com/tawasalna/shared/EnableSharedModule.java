package com.tawasalna.shared;

import com.tawasalna.shared.config.EmailConfig;
import com.tawasalna.shared.config.SharedModuleConfig;
import com.tawasalna.shared.config.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({
        SharedModuleConfig.class,
        SwaggerConfig.class,
        EmailConfig.class
})
@Configuration
public @interface EnableSharedModule {
}
