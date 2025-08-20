package dev.felipeoj.users_crud.application.usecase.users;

import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;

public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(CreateUserRequestDto createUserRequestDto) {
        if (userRepository.existsByEmail(createUserRequestDto.email())) {
            throw new EmailAlreadyExistsException(createUserRequestDto.email());
        }
        if (userRepository.existsByUsername(createUserRequestDto.username())) {
            throw new UsernameAlreadyExistsException(createUserRequestDto.username());
        }

        User newUser = User.builder()
                .username(createUserRequestDto.username())
                .email(createUserRequestDto.email())
                .firstName(createUserRequestDto.firstName())
                .lastName(createUserRequestDto.lastName())
                .build();
        return userRepository.save(newUser);
    }
}
