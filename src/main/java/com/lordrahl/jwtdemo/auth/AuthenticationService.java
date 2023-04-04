package com.lordrahl.jwtdemo.auth;

import com.lordrahl.jwtdemo.config.JwtService;
import com.lordrahl.jwtdemo.user.Role;
import com.lordrahl.jwtdemo.user.User;
import com.lordrahl.jwtdemo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user into the system
     *
     * @param request expected request body
     * @return AuthenticationResponse which contains the bearer token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
