package com.example.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Waza API").version("1.0").description("Primera implementación de Swagger con la api del Ecommerce"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi2() {
        return GroupedOpenApi.builder()
                .group("public-api2")
                .pathsToMatch("/marcas/**")
                .build();
    }
}
