package co.com.authentication.api.router;

import co.com.authentication.model.user.User;
import co.com.authentication.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Mono;

public class UserHandlerTest {

    private UserUseCase userUseCase;
    private UserHandler userHandler;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        userUseCase = Mockito.mock(UserUseCase.class);
        userHandler = new UserHandler(userUseCase);

        webTestClient = WebTestClient.bindToRouterFunction(
                RouterFunctions.route()
                        .POST("/api/v1/users", userHandler::save)
                        .build()
        ).build();
    }

    @Test
    void CrearUsuarioExitosamente() {
        // Arrange
        User user = User.builder()
                .firstName("Carlos")
                .lastName("Ramirez")
                .email("carlos@test.com")
                .baseSalary(5000)
                .build();

        Mockito.when(userUseCase.create(Mockito.any(User.class)))
                .thenReturn(Mono.just("El usuario ha sido creado"));

        webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("El usuario ha sido creado");
    }

    @Test
    void deberiaRetornarBadRequestCuandoLaValidacionFalla() {
        User invalidUser = User.builder()
                .firstName("")
                .lastName("Perez")
                .email("badEmail")
                .baseSalary(0)
                .build();

        Mockito.when(userUseCase.create(Mockito.any(User.class)))
                .thenReturn(Mono.error(new RuntimeException("Datos inválidos")));

        webTestClient.post()
                .uri("/api/v1/users")
                .bodyValue(invalidUser)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Datos inválidos");
    }




}
