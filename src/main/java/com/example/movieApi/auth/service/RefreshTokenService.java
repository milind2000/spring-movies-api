package com.example.movieApi.auth.service;

import com.example.movieApi.auth.entities.RefreshToken;
import com.example.movieApi.auth.entities.User;
import com.example.movieApi.auth.repository.RefreshTokenRepository;
import com.example.movieApi.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository){
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null) {
            long refreshTokenValidity = 30 * 100000;
            refreshToken = RefreshToken.create(
                    UUID.randomUUID().toString(),
                    Instant.now().plusMillis(refreshTokenValidity),
                    user);
            refreshTokenRepository.save(refreshToken);
        }


        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh Token not found"));
        if(refToken.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Token is expired");
        }
        return  refToken;
    }


}

