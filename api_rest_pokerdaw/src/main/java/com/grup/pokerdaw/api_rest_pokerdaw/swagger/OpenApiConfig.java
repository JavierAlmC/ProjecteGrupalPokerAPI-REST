package com.grup.pokerdaw.api_rest_pokerdaw.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

        info = @Info(
                contact = @Contact(
                    name = "Equip04",
                    email = "equip04@gmail.com",
                    url = "https://google.com"
                ),
                description = "OpenApi documentation for spring security",
                title = "OpenApi specification - Equip04",
                version = "1.0",
                license = @License(
                    name = "License Equip04",
                    url = "http://equip04.com"
                ),
                termsOfService = "https://ieslluisimarro.org/termsServices" 
        
        ),
        servers = {
                @Server(
                    description = "Local ENV",
                    url = "http://localhost:8090"
                ),
                @Server(
                    description = "Prod ENV",
                    url = "http://equip06/productionAPI"
                ),

        },
        security = {
                @SecurityRequirement(
                    name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {  
}