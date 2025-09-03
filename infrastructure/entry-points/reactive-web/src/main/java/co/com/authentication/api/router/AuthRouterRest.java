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
public class AuthRouterRest {

    private final RouterProperties properties;

    @Bean
    public RouterFunction<ServerResponse> routerAuthFunction(AuthHandler authHandler) {
        String loginRoute = properties.getPathBase().concat(properties.getPathAuth()).concat(properties.getLogin());

        log.info("Registrando endpoint [POST {}] para login", loginRoute);

        return RouterFunctions.route()
                .POST(loginRoute, authHandler::login)
                .build();
    }
}
