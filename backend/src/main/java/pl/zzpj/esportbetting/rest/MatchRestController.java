package pl.zzpj.esportbetting.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.esportbetting.interfaces.MatchService;
import pl.zzpj.esportbetting.model.Match;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchRestController {
    private final MatchService matchService;

    @Autowired
    public MatchRestController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping()
    public ResponseEntity<List<Match>> findAll(){
        return ResponseEntity.ok(matchService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Match> findById(@PathVariable("id") long id){
        return ResponseEntity.ok(matchService.findById(id));
    }
}
