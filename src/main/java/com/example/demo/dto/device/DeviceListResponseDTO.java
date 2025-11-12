package com.example.demo.dto.device;

import java.util.List;

import lombok.Builder;


@Builder
public record DeviceListResponseDTO(
    List<DeviceResponseDTO> devices
) {}
