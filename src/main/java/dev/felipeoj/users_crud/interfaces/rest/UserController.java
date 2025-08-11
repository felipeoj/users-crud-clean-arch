package dev.felipeoj.users_crud.interfaces.rest;


import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody CreateUserRequestDto requestDto
            ){
        UserResponseDto responseDto = userService.createPerson(requestDto);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + responseDto.username()))
                .body(responseDto);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequestDto requestDto
    ){
        UserResponseDto responseDto = userService.updatePerson(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
