package dev.felipeoj.users_crud.application.service;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.application.dto.response.AuthResponseDto;
import dev.felipeoj.users_crud.application.usecase.auth.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final LoginUseCase loginUseCase;

    public ResponseEntity<Void> login(LoginRequestDto requestDto) {
        AuthResponseDto authResponse = loginUseCase.login(requestDto);
        ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.accessToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofSeconds(authResponse.expiresIn()))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
