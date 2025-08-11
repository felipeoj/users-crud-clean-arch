package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.usecase.CreateUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final CreateUserUseCase createUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public UserResponseDto createPerson(CreateUserRequestDto request){
        return createUserUseCase.createUser(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName()
        );
    }
}
