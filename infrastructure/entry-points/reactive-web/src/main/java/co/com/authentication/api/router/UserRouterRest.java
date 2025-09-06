package co.com.authentication.api.router;

import co.com.authentication.api.config.RouterProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class UserRouterRest {

    private final RouterProperties properties;

    @Bean
    public RouterFunction<ServerResponse> routerCountryFunction(UserHandler userHandler) {
        var route = createRoute(properties.getSave());
        var routeDocument = createRoute(properties.getExistByDocument());

        log.info("Registrando endpoint [POST {}]", route);

        return RouterFunctions.route()
                .POST(route, userHandler::save)
                .GET(routeDocument, userHandler::existByDocument)
                .build();
    }

    private String createRoute(String route){
        String finalRoute = properties.getPathBase().concat(properties.getPathUser()).concat(route);

        log.debug("Ruta construida: {}", finalRoute);

        return finalRoute;
    }

}
