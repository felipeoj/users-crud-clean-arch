package dev.felipeoj.users_crud.application.usecase;

import dev.felipeoj.users_crud.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class SoftDeleteUseCase {

    private final UserRepository userRepository;

    public SoftDeleteUseCase (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        userRepository.softDeleteById(id);
    }
}
