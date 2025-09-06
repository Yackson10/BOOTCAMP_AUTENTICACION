package co.com.authentication.model.user.gateways;

import co.com.authentication.model.user.Rol;
import co.com.authentication.model.user.User;
import reactor.core.publisher.Mono;

public interface IRolRepository {

    Mono<Rol> findById(Long id);

}
