package pl.zzpj.esportbetting.services;

import pl.zzpj.esportbetting.model.Match;

import java.util.List;

public interface MatchService {
    List<Match> findAll();
}
