package dev.felipeoj.users_crud.infrastructure.config;

import dev.felipeoj.users_crud.application.usecase.auth.LoginUseCase;
import dev.felipeoj.users_crud.application.usecase.auth.RegisterUserUseCase;
import dev.felipeoj.users_crud.application.usecase.users.CreateUserUseCase;
import dev.felipeoj.users_crud.application.usecase.users.GetAllUsersUseCase;
import dev.felipeoj.users_crud.application.usecase.users.SoftDeleteUseCase;
import dev.felipeoj.users_crud.application.usecase.users.UpdateUserUseCase;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.infrastructure.persistence.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository){
        return new UpdateUserUseCase(userRepository);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(UserRepository userRepository, UserMapper userMapper) {
        return new GetAllUsersUseCase(userRepository, userMapper);
    }

    @Bean
    public SoftDeleteUseCase softDeleteUseCase(UserRepository userRepository){
        return new SoftDeleteUseCase(userRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepository userRepository,
            PasswordEncoder customPasswordEncoder) {
        return new RegisterUserUseCase(userRepository, customPasswordEncoder);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService
    ) {
        return new LoginUseCase(userRepository, passwordEncoder, jwtTokenService);
    }


}
