package com.example.movieApi.auth.service;

import com.example.movieApi.auth.entities.RefreshToken;
import com.example.movieApi.auth.entities.User;
import com.example.movieApi.auth.entities.UserRoles;
import com.example.movieApi.auth.repository.UserRepository;
import com.example.movieApi.auth.utils.AuthResponse;
import com.example.movieApi.auth.utils.LoginRequest;
import com.example.movieApi.auth.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        var user = User.create(
                registerRequest.getName(),
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                UserRoles.USER
        );
        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

        return AuthResponse.create(accessToken,refreshToken.getRefreshToken());
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        var user = userRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return AuthResponse.create(accessToken, refreshToken.getRefreshToken());
    }
}
