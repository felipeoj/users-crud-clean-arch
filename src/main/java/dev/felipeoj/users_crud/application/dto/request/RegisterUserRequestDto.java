package dev.felipeoj.users_crud.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequestDto(
        @Pattern(
                regexp = "^(?![.])(?!.*[.]$)[a-zA-Z0-9_.]+$"
        )
        @Size(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres")
        @NotBlank
        String username,

        @Email(message = "Email deve ser válido")
        @NotBlank
        String email,

        @Size(min = 2, max = 50)
        String firstName,

        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "Senha não pode estar vazia")
        String password
) {
}
