package dev.mvvasilev.model.dto;

import java.time.LocalDateTime;

public class AccessTokenDTO {

    private String accessToken;

    private String refreshToken;

    private LocalDateTime accessTokenIssuedOn;

    private LocalDateTime accessTokenExpiresAt;

    private LocalDateTime refreshTokenIssuedOn;

    private LocalDateTime refreshTokenExpiresAt;

    public AccessTokenDTO() {
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

    public LocalDateTime getAccessTokenIssuedOn() {
        return accessTokenIssuedOn;
    }

    public void setAccessTokenIssuedOn(LocalDateTime accessTokenIssuedOn) {
        this.accessTokenIssuedOn = accessTokenIssuedOn;
    }

    public LocalDateTime getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(LocalDateTime accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    public LocalDateTime getRefreshTokenIssuedOn() {
        return refreshTokenIssuedOn;
    }

    public void setRefreshTokenIssuedOn(LocalDateTime refreshTokenIssuedOn) {
        this.refreshTokenIssuedOn = refreshTokenIssuedOn;
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(LocalDateTime refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }
}
