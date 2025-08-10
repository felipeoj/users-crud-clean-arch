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

    public JpaUserRepository(SpringDataJpaUserRepository jpaRepo){
        this.jpaRepo = jpaRepo;

    }

    @Override
    public User save(User user){
        UserEntity entity = UserMapper.INSTANCE.toEntity(user);
        UserEntity savedEntity = jpaRepo.save(entity);
        return UserMapper.INSTANCE.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID userId){
        Optional<UserEntity> foundEntity = jpaRepo.findById(userId);
        return foundEntity.map(entity -> UserMapper.INSTANCE.toDomain(entity));

    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = jpaRepo.findAll();
        return entities.stream()
                .map(entity -> UserMapper.INSTANCE.toDomain(entity))
                .collect(Collectors.toList());
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
