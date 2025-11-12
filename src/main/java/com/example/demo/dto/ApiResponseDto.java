package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공통 응답 포맷")
public class ApiResponseDto<T> {
    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "응답 데이터")
    private T data;
}
