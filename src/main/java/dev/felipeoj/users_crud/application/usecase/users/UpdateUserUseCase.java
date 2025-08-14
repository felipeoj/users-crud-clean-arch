package dev.felipeoj.users_crud.application.usecase.users;

import dev.felipeoj.users_crud.application.dto.request.UpdateUserRequestDto;
import dev.felipeoj.users_crud.domain.exception.EmailAlreadyExistsException;
import dev.felipeoj.users_crud.domain.exception.UserNotFoundException;
import dev.felipeoj.users_crud.domain.exception.UsernameAlreadyExistsException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;

import java.util.UUID;

public class UpdateUserUseCase {
    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId, UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if(requestDto.email() != null &&
        !requestDto.email().equals(user.getEmail()) &&
                userRepository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        if(requestDto.username() != null &&
        !requestDto.username().equals(user.getUsername()) &&
        userRepository.existsByUsername(requestDto.username())) {
            throw new UsernameAlreadyExistsException(user.getFirstName());
        }

        if (requestDto.username() != null) {
            user.setUsername(requestDto.username());
        }
        if (requestDto.firstName() != null) {
            user.setFirstName(requestDto.firstName());
        }
        if (requestDto.lastName() != null) {
            user.setLastName(requestDto.lastName());
        }
        if (requestDto.email() != null) {
            user.setEmail(requestDto.email());
        }

        return userRepository.save(user);
    }
}
