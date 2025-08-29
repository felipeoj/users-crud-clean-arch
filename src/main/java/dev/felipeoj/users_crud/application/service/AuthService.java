package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.application.dto.response.AuthResponseDto;
import dev.felipeoj.users_crud.application.usecase.auth.LoginUseCase;
import dev.felipeoj.users_crud.application.usecase.auth.RefreshTokenUseCase;
import dev.felipeoj.users_crud.domain.exception.InvalidCredentialsException;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import dev.felipeoj.users_crud.infrastructure.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final LoginUseCase loginUseCase;
    private final TokenService tokenService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenUseCase  refreshTokenUseCase;

    public ResponseEntity<Void> login(LoginRequestDto requestDto) {
        AuthResponseDto authResponse = loginUseCase.login(requestDto);
        ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.accessToken())
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(authResponse.expiresIn()))
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.refreshToken())
                .httpOnly(true)
//                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofDays(3))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    public ResponseEntity<Void> logout(
            @CookieValue(value = "jwt", required = false) String accessToken,
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    )  {
        if(accessToken != null && refreshToken != null) {
            String userId = jwtTokenService.extractUserId(accessToken);
            jwtTokenService.invalidateToken(accessToken);
            jwtTokenService.invalidateRefreshToken(refreshToken);
        }

        ResponseCookie accessCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
//                .secure(true)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
//                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    public ResponseEntity<Void> refresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {
        try {
            if (refreshToken != null && jwtTokenService.refreshTokenExists(refreshToken)) {
                AuthResponseDto tokens = refreshTokenUseCase.execute(refreshToken);

                ResponseCookie accessCookie = ResponseCookie.from("jwt", tokens.accessToken())
                        .httpOnly(true)
//                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .maxAge(Duration.ofMinutes(30))
                        .build();

                ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
                        .httpOnly(true)
//                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .maxAge(Duration.ofDays(3))
                        .build();
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                        .build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }catch (InvalidCredentialsException | UsernameNotFoundException e) {
            log.warn("Refresh inválido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
    }
}
