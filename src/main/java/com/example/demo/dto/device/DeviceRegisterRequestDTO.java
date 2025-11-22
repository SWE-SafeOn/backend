package com.example.demo.dto.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeviceRegisterRequestDto {

    @NotBlank
    private String vendor;

    private String ip;

    @NotBlank
    private String macAddr;

    private Boolean discovered;

    private String label;

    public DeviceRegisterRequestDto(
            String vendor,
            String ip,
            String macAddr,
            Boolean discovered,
            String label
    ) {
        this.vendor = vendor;
        this.ip = ip;
        this.macAddr = macAddr;
        this.discovered = discovered;
        this.label = label;
    }
}
