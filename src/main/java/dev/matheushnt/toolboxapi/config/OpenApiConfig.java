package dev.matheushnt.toolboxapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Toolbox API")
                    .version("1.0.0")
                    .description("API para gerenciamento de ferramentas.")
                    .contact(new Contact()
                        .name("Matheus Henrique")
                        .email("matheushnt.dev@gmail.com")
                    )
            )
            .components(
                new Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("Autenticação utilizando JSON Web Token.")
                    )
            );
    }
}
