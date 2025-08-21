package dev.felipeoj.users_crud.domain.service;

import dev.felipeoj.users_crud.domain.model.User;

public interface JwtTokenService {
    String generateToken(User user);
    String extractUsername(String token);
    boolean validateToken(String token);
}
