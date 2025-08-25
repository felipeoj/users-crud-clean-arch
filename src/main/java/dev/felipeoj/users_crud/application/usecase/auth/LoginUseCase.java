package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.application.dto.response.AuthResponseDto;
import dev.felipeoj.users_crud.domain.exception.InvalidCredentialsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private static final long TOKEN_EXPIRATION_SECONDS = 1800L;

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findByUsernameOrEmail(loginRequestDto.loginId());

        if(user.isEmpty() || !passwordEncoder.matches(loginRequestDto.password(), user.get().getPassword().getValue())) {
            throw new InvalidCredentialsException();
        }
        String accessToken = jwtTokenService.generateToken(user.get());
        String refreshToken = jwtTokenService.generateRefreshToken(user.get().getId().toString());
        return new AuthResponseDto("Bearer",
                accessToken,
                refreshToken,
                TOKEN_EXPIRATION_SECONDS);
    }
}
