package dev.felipeoj.users_crud.interfaces.rest;


import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
