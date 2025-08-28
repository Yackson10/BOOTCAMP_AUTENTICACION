package co.com.authentication.usecase.user;

import co.com.authentication.model.user.User;
import co.com.authentication.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User userValido;

    @BeforeEach
    void setUp() {
        userValido = User.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@example.com")
                .baseSalary(5000.0)
                .build();
    }

    @Test
    void crearUsuarioExitoso() {
        when(userRepository.emailDuplicate(userValido)).thenReturn(Mono.just(false));
        when(userRepository.save(userValido)).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.create(userValido))
                .expectNext("El usuario ha sido creado")
                .verifyComplete();
    }

    @Test
    void crearUsuarioConEmailDuplicado() {
        when(userRepository.emailDuplicate(userValido)).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.create(userValido))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().equals("El email se encuentra duplicado"))
                .verify();
    }

    @Test
    void crearUsuarioConEmailInvalido() {
        User user = userValido.toBuilder().email("correo-invalido").build();

        StepVerifier.create(userUseCase.create(user))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().equals("Faltan datos por ingresar"))
                .verify();
    }

    @Test
    void crearUsuarioConSalarioMenor() {
        User user = userValido.toBuilder().baseSalary(0.5).build();

        StepVerifier.create(userUseCase.create(user))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("mayor o igual a"))
                .verify();
    }

    @Test
    void crearUsuarioConSalarioMayor() {
        User user = userValido.toBuilder().baseSalary(20_000_000.0).build();

        StepVerifier.create(userUseCase.create(user))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("menor o igual a"))
                .verify();
    }

}
