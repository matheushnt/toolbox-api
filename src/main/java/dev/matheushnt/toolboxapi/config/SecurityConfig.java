package dev.matheushnt.toolboxapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final int ITERATIONS = 3;
    private static final int MEM_LIMIT = 65536;
    private static final int HASH_LENGTH = 32;
    private static final int SALT_LENGTH = 16;
    private static final int PARALLELISM = 1;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEM_LIMIT, ITERATIONS);
    }

}
