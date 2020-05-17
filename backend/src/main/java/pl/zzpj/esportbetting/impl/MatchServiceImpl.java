package pl.zzpj.esportbetting.impl;

import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.services.MatchService;

import java.util.List;

public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }
}
