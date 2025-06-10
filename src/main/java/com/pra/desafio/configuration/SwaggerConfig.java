package com.pra.desafio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio API Title")
                        .description("Desafio API Description")
                        .version("1.0")
                        .termsOfService("urn:tos")
                        .contact(new Contact()
                                .name("Paulo Assumpção")
                                .url(null)
                                .email("paulo.assumpcao@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}
