package dev.felipeoj.users_crud.application.dto.response;

import dev.felipeoj.users_crud.domain.model.User;

import java.util.UUID;

public record UserResponseDto(
        String username,
        String email,
        String firstName,
        String lastName,
        UUID id

) {
    public static UserResponseDto fromDomain(User user){
        return new UserResponseDto(
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }
}