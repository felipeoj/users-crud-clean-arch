package dev.felipeoj.users_crud.domain.repository;

import dev.felipeoj.users_crud.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID userId);
    List<User> findAll();
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void softDeleteById(UUID userId);
    void hardDeleteById(UUID userId);
}
