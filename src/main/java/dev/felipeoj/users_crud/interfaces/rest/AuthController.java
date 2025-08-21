package dev.felipeoj.users_crud.interfaces.rest;

import dev.felipeoj.users_crud.application.dto.request.LoginRequestDto;
import dev.felipeoj.users_crud.application.dto.request.RegisterUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.service.AuthService;
import dev.felipeoj.users_crud.application.usecase.auth.LoginUseCase;
import dev.felipeoj.users_crud.application.usecase.auth.RegisterUserUseCase;
import dev.felipeoj.users_crud.domain.exception.InvalidCredentialsException;
import dev.felipeoj.users_crud.domain.service.UserService;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserMapper userMapper;
    private final LoginUseCase  loginUseCase;
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody RegisterUserRequestDto requestDto
    ){
        UserResponseDto responseDto = userMapper.mapToResponseDto(registerUserUseCase.execute(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais Invalidas.");
    }
}
