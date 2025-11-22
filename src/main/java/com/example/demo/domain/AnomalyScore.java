package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "anomaly_scores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyScore {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "score_id")
    private UUID scoreId;

    private OffsetDateTime ts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packet_meta_id", unique = true)
    private PacketMeta packetMeta;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "anomalyScore")
    private Alert alert;

    @Column(name = "iso_score")
    private Double isoScore;

    @Column(name = "lstm_score")
    private Double lstmScore;

    @Column(name = "hybrid_score")
    private Double hybridScore;

    @Column(name = "is_anom")
    private Boolean isAnom;

    public void setPacketMeta(PacketMeta packetMeta) {
        this.packetMeta = packetMeta;
        if (packetMeta != null && packetMeta.getAnomalyScore() != this) {
            packetMeta.setAnomalyScore(this);
        }
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        if (alert != null && alert.getAnomalyScore() != this) {
            alert.setAnomalyScore(this);
        }
    }
}
