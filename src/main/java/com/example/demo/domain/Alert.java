package com.example.demo.domain;

import com.example.demo.exception.AccessDeniedException;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "alert_id")
    private UUID alertId;

    private OffsetDateTime ts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anomaly_score_id", unique = true)
    private AnomalyScore anomalyScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    private String severity;
    private String reason;

    @Column(columnDefinition = "jsonb")
    private String evidence;

    private String status;

    public void acknowledge() {
        this.status = "ACKNOWLEDGED";
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void ensureDevice(Device expectedDevice) {
        if (device == null || !device.getDeviceId().equals(expectedDevice.getDeviceId())) {
            throw new AccessDeniedException("Alert does not belong to the provided device.");
        }
    }

    public void setAnomalyScore(AnomalyScore anomalyScore) {
        this.anomalyScore = anomalyScore;
        if (anomalyScore != null && anomalyScore.getAlert() != this) {
            anomalyScore.setAlert(this);
        }
    }
}
