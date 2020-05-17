package pl.zzpj.esportbetting.response;

import lombok.Builder;
import lombok.Data;
import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.Team;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MatchResponse {
    private long id;
    private LocalDateTime startDate;
    private int realId;
    private boolean isFinished;
    private int realScoreA;
    private int realScoreB;
    private Team teamA;
    private Team teamB;
    private int stakeA;
    private int stakeB;
    private List<Comment> comments;
}
