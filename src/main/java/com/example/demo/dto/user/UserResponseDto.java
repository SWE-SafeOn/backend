package com.example.demo.dto.user;

import com.example.demo.entity.User;
import lombok.Builder;


@Builder
public record UserResponseDto(
        String id,
        String email,
        String name,
        String created_at
) {
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .name(user.getName())
                .created_at(user.getCreatedAt().toString())
                .build();
    }
}
