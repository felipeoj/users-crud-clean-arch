package dev.felipeoj.users_crud.application.usecase.users;

import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UserNotFoundException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.model.valueobjects.Email;
import dev.felipeoj.users_crud.domain.model.valueobjects.Username;
import dev.felipeoj.users_crud.domain.repository.UserRepository;

import java.util.UUID;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId, UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        if(requestDto.email() != null &&
        !requestDto.email().equals(user.getEmail().getValue()) &&
                userRepository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyExistsException(user.getEmail().getValue());
        }

        if(requestDto.username() != null &&
        !requestDto.username().equals(user.getUsername().getValue()) &&
        userRepository.existsByUsername(requestDto.username())) {
            throw new UsernameAlreadyExistsException(user.getUsername().getValue());
        }

        User updatedUser = User.builder()
                .username(requestDto.username() != null ? new Username(requestDto.username()) : user.getUsername())
                .firstName(requestDto.firstName() != null ? requestDto.firstName() : user.getFirstName())
                .lastName(requestDto.lastName() != null ? requestDto.lastName() : user.getLastName())
                .email(requestDto.email() != null ? new Email(requestDto.email()) : user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .build();

        return userRepository.save(updatedUser);
    }
}
