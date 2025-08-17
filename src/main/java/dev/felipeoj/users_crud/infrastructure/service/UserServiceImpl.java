package dev.felipeoj.users_crud.infrastructure.service;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {return userRepository.save(user);}

    @Override
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean existsByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userRepository.existsByEmail(email);
    }


    @Override
    public void deleteById(UUID id) {
        userRepository.hardDeleteById(id);

    }

}