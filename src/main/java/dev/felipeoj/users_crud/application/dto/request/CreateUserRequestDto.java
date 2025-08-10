package dev.felipeoj.users_crud.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(
        @NotBlank String username,
        @Email String email,
        String firstName,
        String lastName
) {}
