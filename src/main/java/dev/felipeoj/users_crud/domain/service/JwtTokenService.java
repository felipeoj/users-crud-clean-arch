package dev.felipeoj.users_crud.domain.service;

import dev.felipeoj.users_crud.domain.model.User;

public interface JwtTokenService {
    String generateAccessToken(User user);
    String generateRefreshToken(String userId);
    String extractUserId(String token);
    boolean validateToken(String token);
    String refreshAccessToken(String refreshToken);
    void invalidateToken(String token);
    void invalidateRefreshToken(String refreshToken);
    public boolean refreshTokenExists(String refreshToken);
}
