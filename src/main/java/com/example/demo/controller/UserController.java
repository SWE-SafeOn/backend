package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.user.LoginRequestDto;
import com.example.demo.dto.user.SignUpRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "User Auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(@RequestBody SignUpRequestDto req) {
        UserResponseDto data = userService.createUser(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(true, data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> login(@RequestBody LoginRequestDto req) {
        UserResponseDto data = userService.login(req);
        return ResponseEntity
                .ok(new ApiResponseDto<>(true, data));
    }
}
