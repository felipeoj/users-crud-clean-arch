package dev.felipeoj.users_crud.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super("Username já esta em uso.");
    }
}
