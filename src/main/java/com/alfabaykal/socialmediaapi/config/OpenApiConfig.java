package com.alfabaykal.socialmediaapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI socialMediaAPI() {

        Contact contact = new Contact();
        contact.setEmail("alfabaykal@gmail.com");
        contact.setName("alfabaykal");
        contact.setUrl("https://github.com/alfabaykal");

        Info info = new Info()
                .title("Social Media API")
                .version("1.0.0")
                .contact(contact)
                .description("This is a simple RESTful API for a social media platform.");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme());

        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("bearerAuth")
                .description("OpenAPI scheme for JWT authentication")
                .scheme("bearer")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }
}
