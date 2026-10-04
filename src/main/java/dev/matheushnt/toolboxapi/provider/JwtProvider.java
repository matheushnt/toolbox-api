package dev.matheushnt.toolboxapi.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class JwtProvider {
    private static final String ISSUER = "toolbox";
    private static final String AUDIENCE = "toolbox-api";

    @Value("${app.secret-key}")
    private String secretKey;

    public String generate(UUID subject) {
        return JWT.create()
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withExpiresAt(getExpiration())
            .withSubject(subject.toString())
            .sign(getAlgorithm());
    }

    public Optional<String> validate(String token) {
        try {
            DecodedJWT decoded = JWT.require(getAlgorithm())
                .withIssuer(ISSUER)
                .withAudience(AUDIENCE)
                .build()
                .verify(token);
            return Optional.of(decoded.getSubject());
        } catch (JWTVerificationException ex) {
            log.warn("Token validation failed: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    private Instant getExpiration() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}
