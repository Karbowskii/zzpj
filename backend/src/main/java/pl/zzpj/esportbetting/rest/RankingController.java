package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.impl.UserToUserResponseConverter;
import pl.zzpj.esportbetting.interfaces.RankingService;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.RankedUserResponse;
import pl.zzpj.esportbetting.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "ranking")
public class RankingController {

    private final RankingService rankingService;
    private final UserContextService userContextService;

    @Autowired
    public RankingController(RankingService rankingService, UserContextService userContextService) {
        this.rankingService = rankingService;
        this.userContextService = userContextService;
    }

    @GetMapping
    public ResponseEntity<List<RankedUserResponse>> ranking() {
        List<RankedUserResponse> ranking = rankingService.getRanking();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<RankedUserResponse> userRank(@PathVariable("username") String username) {
        RankedUserResponse userRank = rankingService.getUserRank(username);
        return ResponseEntity.ok(userRank);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<RankedUserResponse> myRank() {
        User user = this.userContextService.getAuthenticatedUser();
        return userRank(user.getUsername());
    }
}
