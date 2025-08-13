package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.usecase.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.GetAllUsersUseCase;
import dev.felipeoj.users_crud.application.usecase.SoftDeleteUseCase;
import dev.felipeoj.users_crud.application.usecase.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.exception.UserNotFoundException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService{
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final SoftDeleteUseCase softDeleteUseCase;


    private final UserRepository userRepository;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, GetAllUsersUseCase getAllUsersUseCase, SoftDeleteUseCase softDeleteUseCase, UserRepository userRepository) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.softDeleteUseCase = softDeleteUseCase;
        this.userRepository = userRepository;
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

    @Transactional
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

    @Transactional
    public void softDeletePerson(UUID id) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id));
        user.markAsDeleted(); // usando o dominio para fazer o soft delete
    }

    @Transactional
    public void deleteHardPerson(UUID id) {
        userRepository.findById(id);
                if(!userRepository.existsById(id)){
                    throw new UserNotFoundException(id);
                }
        userRepository.hardDeleteById(id);
    }


}
