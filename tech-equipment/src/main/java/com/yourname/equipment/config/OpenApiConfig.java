package com.yourname.equipment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация OpenAPI/Swagger.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Equipment Management System API")
                        .version("1.0.0")
                        .description("API для системы учета техники в компании")
                        .contact(new Contact()
                                .name("Equipment Management")
                                .email("support@example.com")));
    }
}
