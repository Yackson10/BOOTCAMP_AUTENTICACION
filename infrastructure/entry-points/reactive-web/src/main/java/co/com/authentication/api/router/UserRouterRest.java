package co.com.authentication.api.router;

import co.com.authentication.api.config.RouterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class UserRouterRest {

    private final RouterProperties properties;

    @Bean
    public RouterFunction<ServerResponse> routerCountryFunction(UserHandler userHandler) {
        return RouterFunctions.route()
                .POST(createRoute(properties.getSave()), userHandler::save)
                .build();
    }

    private String createRoute(String route){
        return properties.getPathBase().concat(properties.getPathUser()).concat(route);
    }

}
