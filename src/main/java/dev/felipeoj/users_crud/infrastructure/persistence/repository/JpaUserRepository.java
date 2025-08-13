package dev.felipeoj.users_crud.infrastructure.persistence.repository;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.persistence.entity.UserEntity;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

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
    public void softDeleteById(UUID userId) {
        Optional<UserEntity> foundEntity = jpaRepo.findById(userId);

        if (foundEntity.isPresent()) {
            UserEntity entity = foundEntity.get();
            entity.setDeleted(true);
            jpaRepo.save(entity);

        }

    }

    @Override
    public void hardDeleteById(UUID userId) {
        jpaRepo.deleteById(userId);

    }


}
