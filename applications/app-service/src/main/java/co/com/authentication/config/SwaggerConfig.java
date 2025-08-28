package co.com.authentication.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autenticación")
                        .description("Documentación de la API con Swagger y WebFlux y programaciòn reactiva")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Proyecto CrediYA")
                                .email("marthavel2010@gmail.com.com")
                                .url(""))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa")
                        .url("https://github.com/Yackson10"));
    }
}
