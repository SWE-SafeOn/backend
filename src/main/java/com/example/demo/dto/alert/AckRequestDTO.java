package com.example.demo.dto.alert;

import lombok.Builder;

@Builder
public record AckRequestDTO(
        String userId
) {
}
