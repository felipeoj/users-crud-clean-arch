package dev.felipeoj.users_crud.application.dto.response;

public record AuthResponseDto(
        String tokenType,
        String accessToken,
        Long expiresIn
) {
    public AuthResponseDto(String accessToken, Long expiresIn) {
        this("Bearer", accessToken, expiresIn);
    }
}
