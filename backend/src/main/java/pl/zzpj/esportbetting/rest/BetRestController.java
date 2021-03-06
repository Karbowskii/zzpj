package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.interfaces.MatchService;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.request.CreateBetRequest;


import java.util.List;

@RestController
@RequestMapping(path = "bets")
public class BetRestController {

    private final BetService betService;
    private final MatchService matchService;
    private final UserContextService userContextService;

    @Autowired
    public BetRestController(BetService betService, MatchService matchService, UserContextService userContextService) {
        this.betService = betService;
        this.matchService = matchService;
        this.userContextService = userContextService;
    }

    @PostMapping
    public ResponseEntity<Bet> makeBet(@RequestBody CreateBetRequest createBetRequest) {
        User user = userContextService.getAuthenticatedUser();
        Match match = matchService.findById(createBetRequest.getMatchId());
        Bet bet = Bet.builder()
                .match(match)
                .selectedA(createBetRequest.isSelectedA())
                .coins(createBetRequest.getCoins())
                .build();
        Bet createdBet = betService.createBetForUser(user, bet);
        return ResponseEntity.ok(createdBet);
    }

    @GetMapping
    public ResponseEntity<List<Bet>> getAllBetsForUser() {
        User user = userContextService.getAuthenticatedUser();
        return ResponseEntity.ok(betService.getAllBetsForUser(user));
    }


    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Bet>> findAllByUser(@PathVariable("id") long id){
        return ResponseEntity.ok(betService.findAllByUserId(id));
    }
}
