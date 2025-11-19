package com.example.demo.service;

import com.example.demo.domain.Device;
import com.example.demo.domain.Tenant;
import com.example.demo.domain.User;
import com.example.demo.domain.UserDevice;
import com.example.demo.dto.device.DeviceRegisterRequestDto;
import com.example.demo.dto.device.DeviceResponseDto;
import com.example.demo.repository.DeviceRepository;
import com.example.demo.repository.UserDeviceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.UuidParser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private static final String STATUS_UNKNOWN = "UNKNOWN";

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Transactional
    public DeviceResponseDto registerDevice(DeviceRegisterRequestDto request, UUID userId) {
        User user = getUser(userId);
        Tenant tenant = user.getDefaultTenant();
        if (tenant == null) {
            throw new EntityNotFoundException("사용자의 기본 테넌트를 찾을 수 없습니다.");
        }

        Device device = Device.create(
                tenant,
                request.getVendor(),
                request.getModel(),
                request.getMacAddr(),
                request.getCategory(),
                STATUS_UNKNOWN,
                OffsetDateTime.now()
        );

        Device savedDevice = deviceRepository.save(device);
        userDeviceRepository.save(UserDevice.create(user, savedDevice, request.getModel(), OffsetDateTime.now()));

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
