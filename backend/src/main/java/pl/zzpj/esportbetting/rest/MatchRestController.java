package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.services.MatchService;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchRestController {
    private final MatchService matchService;

    @Autowired
    public MatchRestController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("")
    public ResponseEntity<List<Match>> findAll(){
        return ResponseEntity.ok(matchService.findAll());
    }
}