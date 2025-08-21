package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;

public interface UserMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
    UserResponseDto mapToResponseDto(User user);
}