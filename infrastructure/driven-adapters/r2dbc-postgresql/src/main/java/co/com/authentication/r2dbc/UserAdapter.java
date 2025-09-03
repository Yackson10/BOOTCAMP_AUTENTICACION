package co.com.authentication.r2dbc;

import co.com.authentication.model.user.User;
import co.com.authentication.r2dbc.mapper.UserEntityMapper;
import co.com.authentication.r2dbc.repository.UserOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import co.com.authentication.model.user.gateways.UserRepository;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserAdapter implements UserRepository {
    private final UserEntityMapper mapper;
    private final UserOperation userRepository;

    @Override
    public Mono<Boolean> emailDuplicate(User user) {
        log.debug("Verificando si el email [{}] ya existe", user.getEmail());
        return Mono.just(user)
                .map(User::getEmail)
                .flatMap(userRepository::findByEmail)
                .hasElement();
    }

    @Override
    public Mono<User> save(User user) {
        log.info("Guardando usuario en base de datos: {}", user);
        return Mono.just(user)
                .map(mapper::toEntityFromModel)
                .flatMap(userRepository::save)
                . map(mapper::toModelFromEntity);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        log.debug("Buscando usuario por email: {}", email);
        return userRepository.findByEmail(email)
                .map(mapper::toModelFromEntity);
    }
}
