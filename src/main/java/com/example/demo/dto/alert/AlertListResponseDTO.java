package com.example.demo.dto.alert;

import java.util.List;


public record AlertListResponseDTO(
    List<AlertResponseDTO> alerts
) {

}
