package co.com.authentication.usecase.user;

import co.com.authentication.model.user.gateways.IRolRepository;
import co.com.authentication.model.user.gateways.ITokenProvider;
import co.com.authentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final ITokenProvider iTokenProvider;
    private final IRolRepository iRolRepository;

    public Mono<String> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> iRolRepository.findById(user.getIdRol())
                        .map(rol -> {
                            return iTokenProvider.generateToken(user, rol);
                        })
                );
    }
}
