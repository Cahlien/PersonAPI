package dev.crowell.personapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonApiApplication.class, args);
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("PersonAPI for Ventropy")
                        .description("The API for Ventropy's CRUD training apps.")
                        .version("v1.0.0")
                        .license(new License().name("MIT").url("https://mit-license.org/")))
                .externalDocs(new ExternalDocumentation()
                        .description("PersonAPI Documentation")
                        .url("https://crowell.dev"));
    }
}
