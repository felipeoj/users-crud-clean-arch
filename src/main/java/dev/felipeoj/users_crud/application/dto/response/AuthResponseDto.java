package dev.felipeoj.users_crud.application.dto.response;

public record AuthResponseDto(
        String tokenType,
        String accessToken,
        String refreshToken,  // ✅ ADICIONAR ESTE CAMPO
        Long expiresIn
) {
    public AuthResponseDto(String accessToken, String refreshToken, Long expiresIn) {
        this("Bearer", accessToken, refreshToken, expiresIn);
    }
}
