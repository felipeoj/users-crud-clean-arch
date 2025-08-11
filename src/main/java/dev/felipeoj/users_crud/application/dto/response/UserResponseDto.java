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
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }
}