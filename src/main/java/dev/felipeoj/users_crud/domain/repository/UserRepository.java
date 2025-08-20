package dev.felipeoj.users_crud.domain.repository;

import dev.felipeoj.users_crud.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String identifier);
    List<User> findAll();
    List<User> getAllUsers();
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsById(UUID id);

    void softDeleteById(UUID userId);
    void hardDeleteById(UUID userId);
}
