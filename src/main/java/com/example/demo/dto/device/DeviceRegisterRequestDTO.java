package com.example.demo.dto.device;


import lombok.Builder;


@Builder
public record DeviceRegisterRequestDTO(
    String userId,
    String name,
    String category
) {}