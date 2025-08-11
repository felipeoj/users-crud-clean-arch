package dev.felipeoj.users_crud.interfaces.rest;

import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        return ResponseEntity.status(409).body("Email já está em uso.");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(409).body("Username já está em uso.");
    }
}