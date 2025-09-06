package co.com.authentication.model.user.gateways;

import co.com.authentication.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> emailDuplicate (User user);

    Mono<User> save(User user);

    Mono<User> findByEmail(String email);

    Mono<User> findByDocumentNumber(String documentNumber);

}
