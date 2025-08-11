package dev.felipeoj.users_crud.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("Usuario'" + userId + "'não encontrado.");
    }
}
