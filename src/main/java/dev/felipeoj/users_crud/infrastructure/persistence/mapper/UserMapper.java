package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Referência para documentação do MapStruct:
     * https://www.baeldung.com/mapstruct
     */

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "id", source = "id")
    UserResponseDto toResponseDto(User user);


}