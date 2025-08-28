package co.com.authentication.r2dbc.repository;

import co.com.authentication.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserOperation extends R2dbcRepository<UserEntity, Long> {
        Mono<UserEntity> findByEmail(String email);
    }

