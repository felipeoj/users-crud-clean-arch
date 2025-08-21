package dev.felipeoj.users_crud.interfaces.rest;


import dev.felipeoj.users_crud.application.dto.request.CreateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.application.usecase.users.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.users.GetAllUsersUseCase;
import dev.felipeoj.users_crud.application.usecase.users.SoftDeleteUseCase;
import dev.felipeoj.users_crud.application.usecase.users.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final SoftDeleteUseCase  softDeleteUseCase;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> createUser(
            @Valid  @RequestBody CreateUserRequestDto requestDto
            ){
        UserResponseDto responseDto = userMapper.mapToResponseDto(createUserUseCase.execute(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequestDto requestDto
    ){
        UserResponseDto responseDto = UserResponseDto.fromDomain(updateUserUseCase.execute(id, requestDto));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(getAllUsersUseCase.execute());
    }

    @DeleteMapping("/update/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        softDeleteUseCase.execute(id);
    }

    @DeleteMapping("/hard/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable UUID id){
        userRepository.hardDeleteById(id);
    }
}
