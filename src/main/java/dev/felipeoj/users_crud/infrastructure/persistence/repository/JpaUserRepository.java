package dev.felipeoj.users_crud.infrastructure.persistence.repository;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaUserRepository implements UserRepository {
    private final SpringDataJpaUserRepository jpaRepo;
    private final UserMapper userMapper;

    public JpaUserRepository(SpringDataJpaUserRepository jpaRepo, UserMapper userMapper){
        this.jpaRepo = jpaRepo;

        this.userMapper = userMapper;
    }

    @Override
    public User save(User user){
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaRepo.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID userId){
        Optional<UserEntity> foundEntity = jpaRepo.findById(userId);
        return foundEntity.map(userMapper::toDomain);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> foundEntity = jpaRepo.findByEmail(email);
        return foundEntity.map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> foundEntity = jpaRepo.findByUsername(username);
        return foundEntity.map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String identifier) {
        Optional<UserEntity> foundEntity = jpaRepo.findByUsername(identifier);
        return foundEntity.map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> entities = jpaRepo.findAll();
        return entities.stream()
                .map( userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepo.existsByUsername(username);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepo.existsById(id);
    }

    @Override
    @Transactional
    public void softDeleteById(UUID userId) {
        jpaRepo.findById(userId).ifPresent(entity -> {
            entity.setDeleted(true);
        });
    }

    @Override
    public void hardDeleteById(UUID userId) {
        jpaRepo.deleteById(userId);

    }


}
