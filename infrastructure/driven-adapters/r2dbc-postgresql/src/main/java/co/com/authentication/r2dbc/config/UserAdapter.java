package co.com.authentication.r2dbc.config;

import co.com.authentication.model.user.User;
import co.com.authentication.r2dbc.mapper.UserEntityMapper;
import co.com.authentication.r2dbc.repository.UserOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import co.com.authentication.model.user.gateways.UserRepository;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {
    private final UserEntityMapper mapper;
    private final UserOperation userRepository;

    @Override
    public Mono<Boolean> emailDuplicate(User user) {
        return Mono.just(user)
                .map(User::getEmail)
                .flatMap(userRepository::findByEmail)
                .hasElement();
    }

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(mapper::toEntityFromModel)
                .flatMap(userRepository::save)
                . map(mapper::toModelFromEntity);
    }
}
