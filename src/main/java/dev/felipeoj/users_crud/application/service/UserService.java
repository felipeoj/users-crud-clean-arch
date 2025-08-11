package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.usecase.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService{
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    public UserResponseDto createPerson(CreateUserRequestDto request){
        return createUserUseCase.execute(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName()
        );
    }

    public UserResponseDto updatePerson(UUID userId, UpdateUserRequestDto requestDto) {
        User updatedUser = updateUserUseCase.execute(userId, requestDto);
        return new UserResponseDto(

                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getId()
        );
    }
}
