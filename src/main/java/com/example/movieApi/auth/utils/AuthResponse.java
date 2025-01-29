package com.example.movieApi.auth.utils;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;

    public AuthResponse() {

    }
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthResponse create(String accessToken, String refreshToken) {
        return new AuthResponse( accessToken, refreshToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
