package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.application.dto.response.AuthResponseDto;
import dev.felipeoj.users_crud.domain.exception.InvalidCredentialsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import dev.felipeoj.users_crud.domain.service.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private static final long TOKEN_EXPIRATION_SECONDS = 1800L;

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        boolean isEmail = loginRequestDto.loginId().contains("@");

        Optional<User> user = isEmail
                ? userRepository.findByEmail(loginRequestDto.loginId())
                : userRepository.findByUsername(loginRequestDto.loginId());

        if(user.isEmpty() || !passwordEncoder.matches(loginRequestDto.password(), user.get().getPassword())) {
            throw new InvalidCredentialsException();
        }

        String jwtToken = jwtTokenService.generateToken(user.get());
        return new AuthResponseDto(jwtToken, TOKEN_EXPIRATION_SECONDS);

    }
}
