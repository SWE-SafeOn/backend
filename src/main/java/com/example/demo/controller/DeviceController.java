package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.SimpleMessageResponseDto;
import com.example.demo.dto.device.DeviceRegisterRequestDto;
import com.example.demo.dto.device.DeviceResponseDto;
import com.example.demo.security.AuthenticatedUser;
import com.example.demo.service.DeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
@Tag(name = "Device Page")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<java.util.List<DeviceResponseDto>>> getDevices(
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        java.util.List<DeviceResponseDto> data = deviceService.getDevices(currentUser.userId());
        return ResponseEntity.ok(ApiResponseDto.ok(data));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<DeviceResponseDto>> registerDevice(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody DeviceRegisterRequestDto request
    ) {
        DeviceResponseDto data = deviceService.registerDevice(request, currentUser.userId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(data));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponseDto<DeviceResponseDto>> getDevice(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable String deviceId
    ) {
        DeviceResponseDto data = deviceService.getDevice(deviceId, currentUser.userId());
        return ResponseEntity.ok(ApiResponseDto.ok(data));
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponseDto<SimpleMessageResponseDto>> deleteDevice(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable String deviceId
    ) {
        deviceService.deleteDevice(deviceId, currentUser.userId());
        return ResponseEntity.ok(ApiResponseDto.ok(
                SimpleMessageResponseDto.of("device deleted: " + deviceId)
        ));
    }
}
