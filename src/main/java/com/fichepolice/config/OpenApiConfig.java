package com.fichepolice.config;

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
                .info(new Info()
                        .title("Fiche Police API")
                        .version("v1")
                        .description("API de gestion des fiches de police")
                );
    }

    // Bean optionnel pour regrouper / forcer l'exposition des endpoints OpenAPI
    @Bean
    public GroupedOpenApi fichePoliceApi() {
        return GroupedOpenApi.builder()
                .group("fiche-police")
                .pathsToMatch("/**")
                .build();
    }

    // Astuce : après démarrage:
    // - Swagger UI : http://localhost:8080/swagger-ui/index.html  (ou /swagger-ui.html en fonction de la version)
    // - JSON OpenAPI : http://localhost:8080/v3/api-docs
    // Si vous avez un context-path, préfixez ces URLs par ce context-path.
    // Pour changer le chemin de Swagger UI, définissez dans application.properties :
    // springdoc.swagger-ui.path=/mon-chemin/swagger-ui.html
}
