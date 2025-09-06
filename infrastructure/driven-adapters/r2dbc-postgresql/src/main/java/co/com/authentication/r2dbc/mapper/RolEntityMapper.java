package co.com.authentication.r2dbc.mapper;

import co.com.authentication.model.user.Rol;
import co.com.authentication.r2dbc.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolEntityMapper {
    RolEntity toEntityFromModel(Rol objectModel);
    Rol toModelFromEntity(RolEntity objectEntity);

}
