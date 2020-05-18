package pl.zzpj.esportbetting.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.services.MatchService;

import java.io.IOException;
import java.util.List;

@Service("matchService")
@EnableScheduling
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    private final ESportRestApi eSportRestApi;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, ESportRestApi eSportRestApi) {
        this.matchRepository = matchRepository;
        this.eSportRestApi = eSportRestApi;
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Scheduled(cron = "1 * * * * ?")
    public void checkAndUpdateStartedMatches() throws IOException, InterruptedException {
        List<Match> startedMatches = matchRepository.findAllByFinishedIsFalse();
        eSportRestApi.getAllMatches(GameEnum.LEAGUE_OF_LEGENDS);
    }
}
