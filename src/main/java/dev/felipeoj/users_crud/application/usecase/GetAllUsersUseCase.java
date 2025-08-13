package dev.felipeoj.users_crud.application.usecase;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;

import java.util.List;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<User> execute(){
        return userRepository.getAllUsers();
    }
}
