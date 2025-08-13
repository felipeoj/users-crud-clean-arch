package dev.felipeoj.users_crud.infrastructure.persistence.mapper;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Referência para documentação do MapStruct:
     * https://www.baeldung.com/mapstruct
     */

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);


}