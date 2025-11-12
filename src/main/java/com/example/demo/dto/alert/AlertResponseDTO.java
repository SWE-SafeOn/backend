package com.example.demo.dto.alert;

import com.example.demo.entity.Alert;
import lombok.Builder;

@Builder
public record AlertResponseDTO(
        String id,
        String userId,
        String deviceId,
        String type,
        String level,
        String message,
        String timestamp,
        String status
) {

    public static AlertResponseDTO from(Alert alert) {
        return AlertResponseDTO.builder()
                .id(alert.getId() != null ? alert.getId().toString() : null)
                .userId(alert.getUserId().toString())
                .deviceId(alert.getDeviceId().toString())
                .type(alert.getType())
                .level(alert.getLevel())
                .message(alert.getMessage())
                .timestamp(alert.getTimestamp().toString())
                .status(alert.getStatus())
                .build();
    }
}
