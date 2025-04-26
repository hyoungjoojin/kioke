package kioke.journal.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI openAPI() {
    SecurityScheme securityScheme =
        new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .name("Authorization")
            .scheme("bearer")
            .bearerFormat("JWT");

    SecurityRequirement securityRequirement =
        new SecurityRequirement().addList("Bearer Authorization");

    return new OpenAPI()
        .info(new Info().title("Kioke Journal Service").version("0.1.0").description(""))
        .components(new Components().addSecuritySchemes("Bearer Authorization", securityScheme))
        .addSecurityItem(securityRequirement);
  }
}
