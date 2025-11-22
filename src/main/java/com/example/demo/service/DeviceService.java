package com.example.demo.service;

import com.example.demo.domain.Device;
import com.example.demo.domain.User;
import com.example.demo.domain.UserDevice;
import com.example.demo.dto.device.DeviceRegisterRequestDto;
import com.example.demo.dto.device.DeviceResponseDto;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.UserDeviceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UuidParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private static final boolean DEFAULT_DISCOVERED = false;

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Transactional
    public DeviceResponseDto registerDevice(DeviceRegisterRequestDto request, UUID userId) {
        User user = getUser(userId);

        Device device = Device.create(
                request.getVendor(),
                request.getIp(),
                request.getMacAddr(),
                request.getDiscovered() != null ? request.getDiscovered() : DEFAULT_DISCOVERED,
                OffsetDateTime.now()
        );

        Device savedDevice = deviceRepository.save(device);
        userDeviceRepository.save(UserDevice.create(
                user,
                savedDevice,
                request.getLabel() != null ? request.getLabel() : request.getVendor(),
                OffsetDateTime.now()
        ));

        return DeviceResponseDto.from(savedDevice, userId);
    }

    @Transactional
    public void deleteDevice(String deviceId, UUID userId) {
        UserDevice userDevice = getUserDevice(deviceId, userId);
        Device device = userDevice.getDevice();
        userDeviceRepository.findAllByDeviceDeviceId(device.getDeviceId())
                .forEach(userDeviceRepository::delete);
        deviceRepository.delete(device);
    }

    @Transactional(readOnly = true)
    public DeviceResponseDto getDevice(String deviceId, UUID userId) {
        UserDevice userDevice = getUserDevice(deviceId, userId);
        return DeviceResponseDto.from(userDevice.getDevice(), userDevice.getUser().getUserId());
    }

    @Transactional(readOnly = true)
    public List<DeviceResponseDto> getDevices(UUID userId) {
        return userDeviceRepository.findAllByUserUserId(userId)
                .stream()
                .map(DeviceResponseDto::from)
                .toList();
    }

    private UserDevice getUserDevice(String deviceId, UUID userId) {
        UUID deviceUuid = UuidParser.parseUUID(deviceId);
        UserDevice userDevice = userDeviceRepository.getByDeviceAndUser(deviceUuid, userId);
        userDevice.ensureOwner(userId);
        return userDevice;
    }

    private User getUser(UUID userId) {
        return userRepository.getById(userId);
    }
}
