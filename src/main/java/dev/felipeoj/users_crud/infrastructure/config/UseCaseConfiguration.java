package dev.felipeoj.users_crud.infrastructure.config;

import dev.felipeoj.users_crud.application.usecase.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.GetAllUsersUseCase;
import dev.felipeoj.users_crud.application.usecase.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository){
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository){
        return new UpdateUserUseCase(userRepository);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserRepository userRepository){
        return new GetAllUsersUseCase(userRepository);
    }
}
