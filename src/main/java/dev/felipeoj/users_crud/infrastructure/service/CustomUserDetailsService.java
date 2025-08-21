package dev.felipeoj.users_crud.infrastructure.service;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        try {
            UUID userId = UUID.fromString(identifier);
            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException("identifier nao encontrando: " + identifier);
            }

            return new org.springframework.security.core.userdetails.User(
                    user.get().getUsername(),
                    user.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("USER"))
            );
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException("identifier nao encontrando: " + identifier);
        }
    }
}