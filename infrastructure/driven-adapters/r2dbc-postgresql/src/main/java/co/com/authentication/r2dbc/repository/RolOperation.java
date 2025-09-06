package co.com.authentication.r2dbc.repository;

import co.com.authentication.r2dbc.entity.RolEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RolOperation extends R2dbcRepository<RolEntity, Long> {


}
