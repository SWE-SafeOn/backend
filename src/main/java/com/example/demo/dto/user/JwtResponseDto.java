package com.example.demo.dto.user;

public record JwtResponseDto(
        String accessToken,
        String tokenType,
        UserResponseDto user
) {
    public static JwtResponseDto of(String token, UserResponseDto user) {
        return new JwtResponseDto(token, "Bearer", user);
    }
}
