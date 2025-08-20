package dev.felipeoj.users_crud.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.util.HtmlUtils;

public record RegisterUserRequestDto(
        @Pattern(regexp = "^[a-zA-Z0-9._-]{3,20}$", message = "Caracteres inválidos")
        String username,

        @Email
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @Pattern(regexp = "^[\\p{L} .'-]{2,50}$")
        String firstName,

        @Pattern(regexp = "^[\\p{L} .'-]{2,50}$")
        String lastName,

        @NotBlank(message = "Senha não pode estar vazia")
        String password
) {
    public static RegisterUserRequestDto sanitized(String username, String email, String firstName, String lastName, String password) {
        return new RegisterUserRequestDto(
                HtmlUtils.htmlEscape(username.trim()),
                HtmlUtils.htmlEscape(email.trim()),
                HtmlUtils.htmlEscape(firstName.trim()),
                HtmlUtils.htmlEscape(lastName.trim()),
                password
        );
    }
}
