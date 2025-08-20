package dev.felipeoj.users_crud.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("Usuario não encontrado.");
    }
}
