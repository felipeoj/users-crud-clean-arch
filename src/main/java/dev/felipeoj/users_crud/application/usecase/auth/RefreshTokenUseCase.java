package dev.felipeoj.users_crud.application.usecase.auth;

import dev.felipeoj.users_crud.application.dto.response.AuthResponseDto;
import dev.felipeoj.users_crud.domain.exception.UserNotFoundException;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final JwtTokenService jwtTokenService;

    public AuthResponseDto execute (String refreshToken) {
        String userId = jwtTokenService.extractUserId(refreshToken);
        if (userId == null) {
            throw new UserNotFoundException("Invalid refresh token");
        }

        String newAccess = jwtTokenService.refreshAccessToken(refreshToken);
        jwtTokenService.invalidateRefreshToken(refreshToken);
        String newRefresh = jwtTokenService.generateRefreshToken(userId);

        return new AuthResponseDto("Bearer", newAccess, newRefresh, 1800L);
    }
}
