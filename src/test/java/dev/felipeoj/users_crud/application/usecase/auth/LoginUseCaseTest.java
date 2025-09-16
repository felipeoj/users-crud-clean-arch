package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.domain.exception.InvalidCredentialsException;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    private dev.felipeoj.users_crud.domain.model.User buildUser(String id, String username, String email, String hashedPassword) {
        return dev.felipeoj.users_crud.domain.model.User.builder()
                .id(java.util.UUID.fromString(id))
                .username(new dev.felipeoj.users_crud.domain.model.valueobjects.Username(username))
                .email(new dev.felipeoj.users_crud.domain.model.valueobjects.Email(email))
                .firstName("Test")
                .lastName("User")
                .password(new dev.felipeoj.users_crud.domain.model.valueobjects.Password(hashedPassword))
                .build();
    }

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtTokenService jwtTokenService;

    @InjectMocks LoginUseCase loginUseCase;

    @Test
    void login_withUsername_success() {
        var dto = new LoginRequestDto("user_x", "Strong#123");
        var user = buildUser("5ad481bc-98b7-4162-b675-d415c92244d4", "user_x", "user_x@mail.com", "$2a$10$hash");

        when(userRepository.findByUsernameOrEmail(dto.loginId())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(dto.password(), user.getPassword().getValue())).thenReturn(true);
        when(jwtTokenService.generateAccessToken(user)).thenReturn("access.jwt");
        when(jwtTokenService.generateRefreshToken(user.getId().toString())).thenReturn("refresh.jwt");


        var result = loginUseCase.login(dto);

        assertNotNull(result);
        assertEquals("Bearer", result.tokenType());
        assertEquals("access.jwt", result.accessToken());
        assertEquals("refresh.jwt", result.refreshToken());
        assertTrue(result.expiresIn() > 0);

        verify(userRepository).findByUsernameOrEmail("user_x");
        verify(passwordEncoder).matches("Strong#123", "$2a$10$hash");
        verify(jwtTokenService).generateAccessToken(user);
        verify(jwtTokenService).generateRefreshToken(user.getId().toString());
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtTokenService);
        }

        @Test
        void login_withEmail_success() {
        var dto = new LoginRequestDto("user_x@mail.com", "Strong#123");
        var user = buildUser("5ad481bc-98b7-4162-b675-d415c92244d4", "user_x", "user_x@mail.com","$2a$10$hash");

        when(userRepository.findByUsernameOrEmail(dto.loginId())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(dto.password(), user.getPassword().getValue())).thenReturn(true);
        when(jwtTokenService.generateAccessToken(user)).thenReturn("access.jwt");
        when(jwtTokenService.generateRefreshToken(user.getId().toString())).thenReturn("refresh.jwt");

        var result = loginUseCase.login(dto);

        assertNotNull(result);
        assertEquals("Bearer", result.tokenType());
        assertEquals("access.jwt", result.accessToken());
        assertEquals("refresh.jwt", result.refreshToken());
        assertTrue(result.expiresIn() > 0);

        verify(userRepository).findByUsernameOrEmail("user_x@mail.com");
        verify(passwordEncoder).matches("Strong#123", "$2a$10$hash");
        verify(jwtTokenService).generateAccessToken(user);
        verify(jwtTokenService).generateRefreshToken(user.getId().toString());
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtTokenService);

        }

        @Test
        void login_withPassword_fail() {
        var dto = new LoginRequestDto("user_x", "Strong#122");
        var user = buildUser("5ad481bc-98b7-4162-b675-d415c92244d4", "user_x", "user_x@mail.com", "$2a$10$hash");

        when(userRepository.findByUsernameOrEmail(dto.loginId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Strong#122", "$2a$10$hash")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(dto));

        verify(userRepository).findByUsernameOrEmail("user_x");
        verify(passwordEncoder).matches("Strong#122", "$2a$10$hash24");
        verifyNoInteractions(jwtTokenService);

        }
    }