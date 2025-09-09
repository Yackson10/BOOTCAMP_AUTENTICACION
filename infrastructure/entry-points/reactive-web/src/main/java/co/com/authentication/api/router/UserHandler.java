package co.com.authentication.api.router;

import co.com.authentication.api.response.UserResponse;
import co.com.authentication.common.security.JwtHelper;
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
    private final JwtHelper jwtHelper;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var header = serverRequest.headers().asHttpHeaders().toSingleValueMap();
        var token = header.get("Authorization");

        log.info(" Nueva peticion para guardar usuario");

        if (token == null) {
            return ServerResponse.status(401).bodyValue("Token no enviado");
        }

        try {
            if (!jwtHelper.hasRole(token, "admin")) {
                return ServerResponse.status(403).bodyValue("Acceso denegado: rol no autorizado");
            }
        } catch (Exception e) {
            log.error("Error al validar token", e);
            return ServerResponse.status(401).bodyValue("Token inválido o expirado");
        }

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

    public Mono<ServerResponse> existByDocument (ServerRequest serverRequest){
        var document = serverRequest.pathVariable("documentNumber");
        log.info("Petición recibida para validar documento: {}", document);
        return userUseCase.findByDocumentNumber(document)
                .flatMap(user -> {
                    log.info("Documento encontrado: {}", user.getDocumentNumber());
                    return ServerResponse.ok().bodyValue(
                            UserResponse.builder().exists(Boolean.TRUE).build()
                    );
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Documento no encontrado: {}", document);
                    return ServerResponse.ok().bodyValue(
                            UserResponse.builder().exists(Boolean.FALSE).build()
                    );
                }));
    }



}
