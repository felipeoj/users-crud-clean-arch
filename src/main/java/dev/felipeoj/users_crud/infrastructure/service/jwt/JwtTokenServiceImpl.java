package dev.felipeoj.users_crud.infrastructure.service.jwt;

import dev.felipeoj.users_crud.domain.exception.UserNotFoundException;
import dev.felipeoj.users_crud.domain.model.User;
import dev.felipeoj.users_crud.domain.repository.UserRepository;
import dev.felipeoj.users_crud.domain.service.JwtTokenService;
import dev.felipeoj.users_crud.infrastructure.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl  implements JwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final TokenService tokenService;
    private final UserRepository userRepository;


    @Override
    public String generateAccessToken(User user) {

        var claims = JwtClaimsSet.builder()
                .issuer("CRUD-2025")
                .subject(user.getId().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String generateRefreshToken(String userId) {
        var claims = JwtClaimsSet.builder()
                .issuer("CRUD-2025")
                .subject(userId)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(3, ChronoUnit.DAYS))
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        tokenService.saveRefreshToken(
                userId,
                refreshToken,
                Duration.ofDays(3)
        );
        return refreshToken;
    }


    @Override
    public String extractUserId(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

//    public boolean isBlacklisted(String token) {
//        return redisTemplate.hasKey("blacklist:" + token);
//    }

    @Override
    public boolean validateToken(String token) {
        try {
            var jwt = jwtDecoder.decode(token);
            return !tokenService.isBlacklisted(token);
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public String refreshAccessToken(String refreshToken) {

        try {
            String userId = extractUserId(refreshToken);
            if (userId == null) {
                throw new RuntimeException("Refresh token invalido");
            }
            if (!tokenService.refreshTokenExists(userId, refreshToken)) {
                throw new RuntimeException("Refresh token invalido");
            }

            UUID userUuid = UUID.fromString(userId);
            User user = userRepository.findById(userUuid)
                    .orElseThrow(() -> new UserNotFoundException("Usuario nao encontrado"));
            return generateAccessToken(user);
        } catch (
                Exception e
        ){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void invalidateToken(String token) {
        tokenService.addToBlacklist(token);
    }


    @Override
    public void invalidateRefreshToken(String refreshToken) {
        try{
            if(!tokenService.isBlacklisted(refreshToken)) {
                tokenService.addToBlacklist(refreshToken);
            }
            String userId = extractUserId(refreshToken);
            tokenService.removeRefreshToken(userId);
        } catch (Exception e){
            log.error(e.getMessage());
        }

    }

    @Override
    public boolean refreshTokenExists(String refreshToken) {
        try {

            if (tokenService.isBlacklisted(refreshToken)) {
                return false;
            }
            String userId = extractUserId(refreshToken);
            if (userId == null) {
                return false;
            }

            String storedToken = tokenService.getRefreshToken(userId);
            if (storedToken == null) {
                return false;
            }
            boolean isValid = refreshToken.equals(storedToken);

            return isValid;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
