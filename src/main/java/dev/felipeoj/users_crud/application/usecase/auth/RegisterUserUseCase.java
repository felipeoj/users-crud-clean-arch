package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.RegisterUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.PasswordEncoder;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(RegisterUserRequestDto registerUserRequestDto) {
        if (userRepository.existsByEmail(registerUserRequestDto.email())) {
            throw new EmailAlreadyExistsException(registerUserRequestDto.email());
        }
        if (userRepository.existsByUsername(registerUserRequestDto.username())) {
            throw new UsernameAlreadyExistsException(registerUserRequestDto.username());
        }

        String hashedPassword = passwordEncoder.encode(registerUserRequestDto.password());
        User newUser = User.builder()
                .username(registerUserRequestDto.username())
                .email(registerUserRequestDto.email())
                .firstName(registerUserRequestDto.firstName())
                .lastName(registerUserRequestDto.lastName())
                .password(hashedPassword)
                .build();
        return userRepository.save(newUser);
    }
}
