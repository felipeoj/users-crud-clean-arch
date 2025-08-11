package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}