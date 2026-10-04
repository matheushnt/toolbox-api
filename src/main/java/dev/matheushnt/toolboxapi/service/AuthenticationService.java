package dev.matheushnt.toolboxapi.service;

import dev.matheushnt.toolboxapi.dtos.SignInRequest;
import dev.matheushnt.toolboxapi.dtos.SignInResponse;
import dev.matheushnt.toolboxapi.dtos.SignUpRequest;
import dev.matheushnt.toolboxapi.dtos.SignUpResponse;
import dev.matheushnt.toolboxapi.entity.User;
import dev.matheushnt.toolboxapi.enums.UserRole;
import dev.matheushnt.toolboxapi.exception.ResourceFoundException;
import dev.matheushnt.toolboxapi.provider.JwtProvider;
import dev.matheushnt.toolboxapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    public SignUpResponse signUp(SignUpRequest dto) {
        Optional<User> existingUser = repository.findByEmail(dto.email());

        if (existingUser.isPresent()) {
            throw new ResourceFoundException("Email address already registered");
        }

        User user = new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(UserRole.USER);
        repository.save(user);

        return new SignUpResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }

    public SignInResponse signIn(SignInRequest dto) {
        User user = (User) loadUserByUsername(dto.email());
        boolean passwordMatches = passwordEncoder.matches(dto.password(), user.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new SignInResponse(jwtProvider.generate(user.getId()));
    }

}
