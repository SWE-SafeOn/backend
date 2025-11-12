package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.dto.alert.AckRequestDTO;
import com.example.demo.dto.alert.AlertListResponseDTO;
import com.example.demo.dto.alert.AlertResponseDTO;
import com.example.demo.service.AlertService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
@Tag(name = "Alerts")
public class AlertController {

    private final AlertService alertService;

    // 모든 로그 조회
    @GetMapping
    public ResponseEntity<ApiResponseDto<AlertListResponseDTO>> getAlerts(@RequestParam String userId) {
        List<AlertResponseDTO> alerts = alertService.getAlertsByUser(userId);
        AlertListResponseDTO response = new AlertListResponseDTO(alerts);
        return ResponseEntity.ok(new ApiResponseDto<>(true, response));
    }

    // 로그 상세 조회
    @GetMapping("/{alertId}")
    public ResponseEntity<ApiResponseDto<AlertResponseDTO>> getAlert(@PathVariable String alertId) {
        AlertResponseDTO alert = alertService.getAlert(alertId);
        return ResponseEntity.ok(new ApiResponseDto<>(true, alert));
    }

    // 로그 확인 여부
    @PostMapping("/{alertId}/ack")
    public ResponseEntity<ApiResponseDto<AlertResponseDTO>> acknowledgeAlert(
            @PathVariable String alertId,
            @RequestBody AckRequestDTO request
    ) {
        AlertResponseDTO alert = alertService.acknowledgeAlert(alertId, request);
        return ResponseEntity.ok(new ApiResponseDto<>(true, alert));
    }
}
