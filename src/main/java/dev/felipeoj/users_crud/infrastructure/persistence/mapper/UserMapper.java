package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity (User user);

    User toDomain(UserEntity entity);
}
