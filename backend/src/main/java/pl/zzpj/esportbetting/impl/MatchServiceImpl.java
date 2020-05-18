package pl.zzpj.esportbetting.impl;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.GameEnum;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.parsers.MatchApiParser;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.services.MatchService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("matchService")
@EnableScheduling
public class MatchServiceImpl implements MatchService {
    private static final GameEnum GAME_NAME = GameEnum.LEAGUE_OF_LEGENDS;
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);

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
    public void checkAndUpdateMatches() throws IOException, InterruptedException, JSONException {
        LOGGER.info("Checking and updating matches");
        List<Match> matchesFromApi = MatchApiParser.parse(eSportRestApi.getAllMatches(GAME_NAME));
        List<Match> matchesToChange = new ArrayList<>();

        matchRepository.findAllByStatusNotLike(MatchStatusEnum.FINISHED).forEach(match -> matchesToChange.addAll(
                matchesFromApi
                        .stream()
                        .filter(api -> api.getId() == match.getId() && !api.getStatus().equals(match.getStatus()))
                        .collect(Collectors.toList())));

        //TODO: jezeli zmienil sie na canceled znajdz wszystkie betsy i oddaj ludziom zetony

        matchRepository.saveAll(matchesToChange);
        LOGGER.info("Finished checking and updating matches");
    }
}
