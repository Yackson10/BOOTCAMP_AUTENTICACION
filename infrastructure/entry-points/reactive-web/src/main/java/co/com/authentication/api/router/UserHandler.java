package co.com.authentication.api.router;

import co.com.authentication.model.user.User;
import co.com.authentication.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserHandler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info(" Nueva peticion para guardar usuario");

        return serverRequest.bodyToMono(User.class)
                .doOnNext(user -> log.debug("Datos recibidos: {}", user))
                .flatMap(userUseCase::create)
                .doOnSuccess(msg -> log.info("Usuario creado correctamente: {}", msg))
                .doOnError(ex -> log.error("Error al crear usuario", ex))
                .flatMap(msg -> ServerResponse.ok().bodyValue(msg))
                .onErrorResume(RuntimeException.class, ex -> {
                            log.warn("Error de validacion al crear usuario: {}", ex.getMessage());


                        return ServerResponse.badRequest().bodyValue(ex.getMessage());
                });
    }
}
