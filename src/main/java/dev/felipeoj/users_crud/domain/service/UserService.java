package dev.felipeoj.users_crud.domain.service;

import dev.felipeoj.users_crud.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService{
    User save(User user);
    List<User> getAll();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteById(UUID id);

}
