package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.RegisterUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.model.valueobjects.Email;
import dev.felipeoj.users_crud.domain.model.valueobjects.Password;
import dev.felipeoj.users_crud.domain.model.valueobjects.Username;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.messaging.EventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventPublisher eventPublisher;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
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
                .username(new Username(registerUserRequestDto.username()))
                .email(new Email(registerUserRequestDto.email()))
                .firstName(registerUserRequestDto.firstName())
                .lastName(registerUserRequestDto.lastName())
                .password(new Password(hashedPassword))
                .build();

        User savedUser = userRepository.save(newUser);

        eventPublisher.publishUserCreated(
                savedUser.getUsername().getValue(),
                savedUser.getEmail().getValue(),
                Instant.now()
        );

        return savedUser;
    }
}
