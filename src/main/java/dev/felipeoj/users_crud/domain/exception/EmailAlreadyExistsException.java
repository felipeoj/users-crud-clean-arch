package dev.felipeoj.users_crud.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email já esta em uso.");
    }
}
