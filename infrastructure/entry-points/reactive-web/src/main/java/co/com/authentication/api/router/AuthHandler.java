package co.com.authentication.api.router;

import co.com.authentication.usecase.user.LoginUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthHandler {

    private final LoginUseCase loginUseCase;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(Map.class)
                .flatMap(body -> {
                    String email = (String) body.get("email");
                    String password = (String) body.get("password");
                    log.info("Intentando login para {}", email);
                    return loginUseCase.login(email, password);
                })
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("token", token)))
                .onErrorResume(e -> {
                    log.error("Error en login: {}", e.getMessage());
                    return ServerResponse.badRequest()
                            .bodyValue(Map.of("error", e.getMessage()));
                });
    }
}
