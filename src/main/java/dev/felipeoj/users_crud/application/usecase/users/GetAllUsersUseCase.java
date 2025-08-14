package dev.felipeoj.users_crud.application.usecase.users;

import dev.felipeoj.users_crud.application.dto.response.UserResponseDto;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetAllUsersUseCase(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public List<UserResponseDto> execute(){
        List<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
