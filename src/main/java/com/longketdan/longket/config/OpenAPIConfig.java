package com.longketdan.longket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Longket API Document",
        description = "Longket API", version = "v0.1"))

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String authHeader = "Authorization";
        return new OpenAPI()
                .addServersItem(new Server().url("https://api.longboard.monster").description("Production server"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local server"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(authHeader))
                .components(new Components()
                        .addSecuritySchemes(authHeader,
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")));
    }
}
