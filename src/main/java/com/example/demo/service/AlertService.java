package com.example.demo.service;

import com.example.demo.dto.alert.AckRequestDTO;
import com.example.demo.dto.alert.AlertResponseDTO;
import com.example.demo.entity.Alert;
import com.example.demo.repository.AlertRepository;
import com.example.demo.util.UuidParser;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> getAlertsByUser(String userId) {
        UUID userUuid = UuidParser.parseUUID(userId);
        return alertRepository.findAllByUserId(userUuid)
                .stream()
                .map(AlertResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlertResponseDTO getAlert(String alertId) {
        Alert alert = findAlertById(alertId);
        return AlertResponseDTO.from(alert);
    }

    @Transactional
    public AlertResponseDTO acknowledgeAlert(String alertId, AckRequestDTO request) {
        Alert alert = findAlertById(alertId);
        UUID requesterId = UuidParser.parseUUID(request.userId());

        if (!alert.getUserId().equals(requesterId)) {
            throw new IllegalArgumentException("해당 알림에 접근할 권한이 없습니다.");
        }

        alert.setStatus(Alert.STATUS_ACKNOWLEDGED);
        return AlertResponseDTO.from(alertRepository.save(alert));
    }

    private Alert findAlertById(String alertId) {
        UUID alertUuid = UuidParser.parseUUID(alertId);
        return alertRepository.findById(alertUuid)
                .orElseThrow(() -> new EntityNotFoundException("알람이 존재하지 않습니다.: " + alertId));
    }

}
