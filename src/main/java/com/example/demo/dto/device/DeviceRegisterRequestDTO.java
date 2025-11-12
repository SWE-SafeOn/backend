package com.example.demo.dto.device;

import java.util.UUID;

import lombok.Builder;


@Builder
public record DeviceRegisterRequestDTO(
    UUID userId,
    String name,
    String category
) {}