package dev.felipeoj.users_crud.infrastructure.service.jwt;

import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl  implements JwtTokenService {
    private final JwtEncoder jwtEncoder;


    @Override
    public String generateToken(User user) {
        var claims = JwtClaimsSet.builder()
                .issuer("CRUD-2025")
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
