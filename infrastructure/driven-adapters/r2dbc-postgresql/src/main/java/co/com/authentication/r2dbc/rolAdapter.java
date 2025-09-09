package co.com.authentication.r2dbc;

import co.com.authentication.model.user.Rol;
import co.com.authentication.model.user.gateways.IRolRepository;
import co.com.authentication.r2dbc.mapper.RolEntityMapper;
import co.com.authentication.r2dbc.repository.RolOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class rolAdapter implements IRolRepository {

    private final RolEntityMapper rolEntityMapper;
    private final RolOperation rolOperation;

    @Override
    public Mono<Rol> findById(Long id) {
        return rolOperation.findById(id)
                .map(rolEntityMapper::toModelFromEntity);
    }
}
