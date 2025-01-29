package com.example.movieApi.auth.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    private User user;

    // Private constructor for controlled object creation
    private RefreshToken(Integer tokenId, String refreshToken, Instant expirationTime, User user) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    // Public no-arg constructor for JPA
    public RefreshToken() {}

    // Static factory method for controlled instantiation
    public static RefreshToken create(String refreshToken, Instant expirationTime, User user) {
        return new RefreshToken(null, refreshToken, expirationTime, user);
    }

    // Getters
    public Integer getTokenId() {
        return tokenId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public User getUser() {
        return user;
    }
}
