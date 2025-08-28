package co.com.authentication.r2dbc.mapper;

import co.com.authentication.model.user.User;
import co.com.authentication.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
    public interface UserEntityMapper {
        UserEntity toEntityFromModel(User objectModel);
        User toModelFromEntity(UserEntity objectEntity);
    }



