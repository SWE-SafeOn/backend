package com.example.demo.dto.user;

public record SignUpRequestDto(
    String email, 
    String password, 
    String name
) {
    
}
