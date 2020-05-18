package pl.zzpj.esportbetting.model;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime startDate = LocalDateTime.now();

    @Timestamp
    private LocalDateTime endDate;

    @Column(nullable = false)
    private MatchStatusEnum status = MatchStatusEnum.NOT_STARTED;

    @Column(nullable = false, unique = true)
    private int realId;

    @ColumnDefault("0")
    @Column(name = "real_score_A", nullable = false)
    private int realScoreA;

    @ColumnDefault("0")
    @Column(name = "real_score_B", nullable = false)
    private int realScoreB;

    @ManyToOne
    @JoinColumn(name = "team_id_A")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name = "team_id_B")
    private Team teamB;

    @ColumnDefault("1")
    @Column(name = "stake_A", nullable = false)
    private int stakeA;

    @ColumnDefault("1")
    @Column(name = "stake_B", nullable = false)
    private int stakeB;

    @OneToMany(mappedBy = "match",
            cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
