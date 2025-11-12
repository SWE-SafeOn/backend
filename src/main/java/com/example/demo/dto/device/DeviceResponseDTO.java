package com.example.demo.dto.device;

import com.example.demo.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponseDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private String category;
    private String status;
    private LocalDateTime createdAt;

    public static DeviceResponseDTO from(Device device) {
        return DeviceResponseDTO.builder()
                .id(device.getId())
                .userId(device.getUserId())
                .name(device.getName())
                .category(device.getCategory())
                .status(device.getStatus())
                .createdAt(device.getCreatedAt())
                .build();
    }
}
