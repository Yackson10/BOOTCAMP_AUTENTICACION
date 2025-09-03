package co.com.authentication.usecase.user;

import co.com.authentication.model.security.PasswordEncoderService;
import co.com.authentication.model.user.gateways.ITokenProvider;
import co.com.authentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoder;
    private final ITokenProvider iTokenProvider;

    public Mono<String> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                        return Mono.just(iTokenProvider.generateToken(user));
                    } else {
                        return Mono.error(new RuntimeException("Contraseña incorrecta"));
                    }
                });
    }
}

