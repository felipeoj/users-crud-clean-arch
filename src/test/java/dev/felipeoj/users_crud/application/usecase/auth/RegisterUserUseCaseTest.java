package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.RegisterUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    void execute_returnsSavedUser() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);

        RegisterUserRequestDto dto = new RegisterUserRequestDto(
                "user_" + suffix,
                "user" + suffix + "@mail.com",
                "felipe",
                "Oliveira",
                "Test#2024Password"
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("$2a$10$hash");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var result = registerUserUseCase.execute(dto);

        assertNotNull(result);
        assertEquals(dto.username(), result.getUsername().getValue());
        assertEquals(dto.email(), result.getEmail().getValue());
        assertEquals(dto.firstName(), result.getFirstName());
        assertEquals(dto.lastName(), result.getLastName());

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository).existsByUsername(dto.username());
        verify(passwordEncoder).encode(dto.password());
        verify(userRepository).save(any());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void execute_throwsWhenEmailAlreadyExists() {
        RegisterUserRequestDto dto = new RegisterUserRequestDto(
                "user_x", "user_x@mail.com", "felipe", "Oliveira", "Test#2024Password"
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> registerUserUseCase.execute(dto));

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository, never()).existsByUsername(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void execute_throwsWhenUsernameAlreadyExists() {
        RegisterUserRequestDto dto = new RegisterUserRequestDto(
                "user_x", "user_x@mail.com", "felipe", "Oliveira", "Test#2024Password"
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByUsername(dto.username())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> registerUserUseCase.execute(dto));

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository).existsByUsername(dto.username());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    void execute_throwsWhenPasswordVoValidationFails (){
        RegisterUserRequestDto dto = new RegisterUserRequestDto(
                "user_x", "user_x@mail.com", "felipe", "Oliveira", "123"
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("weak");

        assertThrows(IllegalArgumentException.class, () -> registerUserUseCase.execute(dto));

        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository).existsByUsername(dto.username());
        verify(passwordEncoder).encode(dto.password());
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }
}