package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.impl.UserToUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.RankingService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.RankedUserResponse;
import pl.zzpj.esportbetting.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "ranking")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity<List<RankedUserResponse>> ranking() {
        List<RankedUserResponse> ranking = rankingService.getRanking();
        return ResponseEntity.ok(ranking);
    }
}
