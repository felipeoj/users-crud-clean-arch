package dev.felipeoj.users_crud.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres")
        String username,

        @Email(message = "Email deve ser válido")
        String email,

        @Size(min = 2, max = 50)
        String firstName,

        @Size(min = 2, max = 50)
        String lastName
) {
}
