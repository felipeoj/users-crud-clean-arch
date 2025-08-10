package dev.felipeoj.users_crud.application.usecase;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;

public class CreateUserUseCase {
    private final UserRepository userRepository;

    public  CreateUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(String username, String email,
                                       String firstName, String lastName){
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException(username);
        }

        User newUser = new User(
                username,
                email,
                firstName,
                lastName
        );

        User savedUser = userRepository.save(newUser);
        return new UserResponseDto(
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );

    }
}
