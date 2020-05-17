package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.impl.MatchToMatchResponseConverter;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.response.MatchResponse;
import pl.zzpj.esportbetting.services.MatchService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matches")
public class MatchRestController {
    private final MatchService matchService;

    @Autowired
    public MatchRestController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("")
    public ResponseEntity<List<MatchResponse>> findAll(){
        return ResponseEntity.ok(matchService.findAll()
                .stream()
                .map(MatchToMatchResponseConverter::convert)
                .collect(Collectors.toList()));
    }
}
