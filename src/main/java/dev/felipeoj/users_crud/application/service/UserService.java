package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.usecase.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.GetAllUsersUseCase;
import dev.felipeoj.users_crud.application.usecase.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService{
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, GetAllUsersUseCase getAllUsersUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
    }

    public UserResponseDto createPerson(CreateUserRequestDto request){
        User createdUser = createUserUseCase.execute(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName()
        );
        return UserResponseDto.fromDomain(createdUser);
    }

    public UserResponseDto updatePerson(UUID userId, UpdateUserRequestDto requestDto) {
        User updatedUser = updateUserUseCase.execute(userId, requestDto);
        return  UserResponseDto.fromDomain(updatedUser);
    }

    public List<UserResponseDto> getAllPersons() {
        List<User> users = getAllUsersUseCase.execute();
        return users.stream()
                .map(UserResponseDto::fromDomain)
                .collect(Collectors.toList());
    }
}
