package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.model.valueobjects.Username;
import dev.felipeoj.users_crud.domain.model.valueobjects.Email;
import dev.felipeoj.users_crud.domain.model.valueobjects.Password;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(user.getPassword().getValue());
        return entity;
    }

    @Override
    public User toDomain(UserEntity entity) {
        return User.builder()
                .username(new Username(entity.getUsername()))
                .email(new Email(entity.getEmail()))
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .password(new Password(entity.getPassword()))
                .build();
    }

    @Override
    public UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }
}