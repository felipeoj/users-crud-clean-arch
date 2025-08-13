package dev.felipeoj.users_crud.infrastructure.persistence.repository;

import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAll();
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
