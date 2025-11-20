package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "anomaly_scores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnomalyScore {

    @Id
    @Column(name = "score_id")
    @GeneratedValue(generator = "uuid2")
    private UUID scoreId;

    private OffsetDateTime ts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id")
    private Flow flow;

    @Column(name = "iso_score")
    private Double isoScore;

    @Column(name = "lstm_score")
    private Double lstmScore;

    @Column(name = "hybrid_score")
    private Double hybridScore;

    @Column(name = "is_anom")
    private Boolean isAnom;

    public static AnomalyScore create(
            OffsetDateTime ts,
            Flow flow,
            Double isoScore,
            Double lstmScore,
            Double hybridScore,
            Boolean isAnom
    ) {
        AnomalyScore score = new AnomalyScore();
        score.ts = ts;
        score.flow = flow;
        score.isoScore = isoScore;
        score.lstmScore = lstmScore;
        score.hybridScore = hybridScore;
        score.isAnom = isAnom;
        return score;
    }
}
