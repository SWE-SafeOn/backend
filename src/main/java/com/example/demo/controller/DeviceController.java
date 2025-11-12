package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.device.DeviceListResponseDTO;
import com.example.demo.dto.device.DeviceRegisterRequestDTO;
import com.example.demo.dto.device.DeviceResponseDTO;
import com.example.demo.service.DeviceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
@Tag(name = "Device Page")
public class DeviceController {

    private final DeviceService deviceService;

    // 유저별 디바이스 목록 조회 (대시보드용)
    @GetMapping("/list")
    public ResponseEntity<ApiResponseDto<DeviceListResponseDTO>> getDevices(@RequestParam UUID userId) {
        DeviceListResponseDTO data = deviceService.getDevicesByUserId(userId);
        return ResponseEntity
                .ok(new ApiResponseDto<>(true, data));
    }

    // 디바이스 등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<DeviceResponseDTO>> registerDevice(
            @RequestBody DeviceRegisterRequestDTO request) {

        DeviceResponseDTO data = deviceService.registerDevice(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponseDto<>(true, data));
    }

    // 디바이스 단일 조회
    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponseDto<DeviceResponseDTO>> getDevice(@PathVariable UUID deviceId) {
        DeviceResponseDTO data = deviceService.getDevice(deviceId);
        return ResponseEntity
                .ok(new ApiResponseDto<>(true, data));
    }

    // 디바이스 삭제
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<ApiResponseDto<Object>> deleteDevice(@PathVariable UUID deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity
                .ok(new ApiResponseDto<>(true, 
                        java.util.Map.of("deviceId", deviceId, "deleted", true)));
    }
}
