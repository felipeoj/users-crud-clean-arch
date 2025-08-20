package dev.felipeoj.users_crud.application.dto.request;

public record LoginRequestDto(
        String loginId,
        String password
) {
}
