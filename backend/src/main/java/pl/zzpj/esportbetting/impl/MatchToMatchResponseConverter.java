package pl.zzpj.esportbetting.impl;

import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.response.MatchResponse;

public class MatchToMatchResponseConverter {
    public static MatchResponse convert(Match match){
        return MatchResponse.builder()
                .id(match.getId())
                .startDate(match.getStartDate())
                .realId(match.getRealId())
                .isFinished(match.isFinished())
                .teamA(match.getTeamA())
                .teamB(match.getTeamB())
                .stakeA(match.getStakeA())
                .stakeB(match.getStakeB())
                .realScoreA(match.getRealScoreA())
                .realScoreB(match.getRealScoreB())
                .comments(match.getComments())
                .build();
    }
}
