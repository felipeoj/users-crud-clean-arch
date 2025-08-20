package dev.felipeoj.users_crud.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Usuário não encontrado");
    }
}
