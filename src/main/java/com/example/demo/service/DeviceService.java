package com.example.demo.service;

import com.example.demo.dto.device.DeviceListResponseDTO;
import com.example.demo.dto.device.DeviceRegisterRequestDTO;
import com.example.demo.dto.device.DeviceResponseDTO;
import com.example.demo.entity.Device;
import com.example.demo.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Transactional
    public DeviceResponseDTO registerDevice(DeviceRegisterRequestDTO request) {
        Device device = Device.builder()
                .userId(UUID.fromString(request.userId()))
                .name(request.name())
                .category(request.category())
                .status(Device.STATUS_UNKNOWN)
                .build();

        return DeviceResponseDTO.from(deviceRepository.save(device));
    }

    @Transactional
    public void deleteDevice(String deviceId) {
        Device device = getDeviceEntity(deviceId);
        deviceRepository.delete(device);
    }

    @Transactional(readOnly = true)
    public DeviceResponseDTO getDevice(String deviceId) {
        Device device = getDeviceEntity(deviceId);
        return DeviceResponseDTO.from(device);
    }

    @Transactional(readOnly = true)
    public DeviceListResponseDTO getDevicesByUserId(String userId) {
        UUID uuid = parseUUID(userId);

        List<DeviceResponseDTO> responses = deviceRepository.findByUserId(uuid)
                .stream()
                .map(DeviceResponseDTO::from)
                .toList();
                
        return DeviceListResponseDTO.builder()
                .devices(responses)
                .build();
    }

    private Device getDeviceEntity(String deviceId) {
        UUID uuid = parseUUID(deviceId);
        return deviceRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("디바이스를 찾을 수 없습니다 : " + deviceId));
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID 형식이 아닙니다.");
        }
    }
}
