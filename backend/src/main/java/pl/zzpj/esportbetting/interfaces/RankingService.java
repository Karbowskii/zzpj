package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.response.RankedUserResponse;

import java.util.List;

public interface RankingService {
    List<RankedUserResponse> getRanking();
}
