package dev.felipeoj.users_crud.infrastructure.service;

import dev.felipeoj.users_crud.domain.service.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncryptPassword implements PasswordEncoder {
    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
