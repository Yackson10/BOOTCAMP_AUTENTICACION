package co.com.authentication.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/v1/credi-ya/user/**")
                .build();
    }
}

