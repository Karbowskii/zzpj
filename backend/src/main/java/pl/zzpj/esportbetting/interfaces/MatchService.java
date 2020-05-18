package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.Match;

import java.util.List;

public interface MatchService {
    List<Match> findAll();
}
