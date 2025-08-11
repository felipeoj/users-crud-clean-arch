package dev.felipeoj.users_crud.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super(" O Username'" + username + "' já está em uso.");
    }
}
