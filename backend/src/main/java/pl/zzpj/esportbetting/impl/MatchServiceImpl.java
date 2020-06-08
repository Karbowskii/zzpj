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
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.interfaces.MatchService;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.Team;
import pl.zzpj.esportbetting.model.parsers.MatchApiParser;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.repos.TeamRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service("matchService")
@EnableScheduling
public class MatchServiceImpl implements MatchService {
    private static final GameEnum GAME_NAME = GameEnum.LEAGUE_OF_LEGENDS;

    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ESportRestApi eSportRestApi;
    private final BetService betService;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, ESportRestApi eSportRestApi,
                            TeamRepository teamRepository, BetService betService) {
        this.matchRepository = matchRepository;
        this.eSportRestApi = eSportRestApi;
        this.teamRepository = teamRepository;
        this.betService = betService;
    }

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    @Scheduled(cron = "1 * * * * ?")
    public void checkAndUpdateMatches() throws IOException, InterruptedException, JSONException {
        List<Match> matchesFromApi = MatchApiParser.parse(eSportRestApi.getAllMatches(GAME_NAME));
        addNewMatchesToDB(matchesFromApi);
        updateMatches(matchesFromApi);
    }

    public void updateMatches(List<Match> matchesFromApi) {
        List<Match> matchesToChange = new ArrayList<>();
        List<Match> matchesWithStatusNotFinished = matchRepository.findAllByStatusNotLike(MatchStatusEnum.FINISHED);
        matchesWithStatusNotFinished.forEach(
                match -> matchesToChange.addAll(
                        matchesFromApi
                                .stream()
                                .filter(api -> api.getRealId() == match.getRealId()
                                        && !api.getStatus().equals(match.getStatus()))
                                .peek(api -> {
                                    api.setId(match.getId());
                                    api.setTeamA(match.getTeamA());
                                    api.setTeamB(match.getTeamB());
                                })
                                .collect(Collectors.toList())));
        matchesToChange.forEach(m -> {
            if (m.getStatus().equals(MatchStatusEnum.FINISHED)) {
                betService.withdrawBetMoneyAndExpForMatch(m);
            }
        });
        matchRepository.saveAll(matchesToChange);

        List<Match> matchesToDelete = new ArrayList<>();
        matchesToChange.stream().filter(m -> m.getStatus().equals(MatchStatusEnum.CANCELED))
                .collect(Collectors.toList())
                .forEach(match -> {
                    betService.returnCoinsIfMatchCanceled(match);
                    matchesToDelete.add(match);
                });
        matchRepository.deleteAll(matchesToDelete);

        logger.info("Finished updating " + matchesToChange.size() + " matches");
    }

    public void addNewMatchesToDB(List<Match> allMatchesFromApi) {
        List<Match> newMatches = allMatchesFromApi
                .stream()
                .filter(m -> matchRepository.findByRealId(m.getRealId()).isEmpty()
                        && !m.getStatus().equals(MatchStatusEnum.CANCELED))
                .collect(Collectors.toList());
        newMatches.forEach(m -> {
            Optional<Team> teamA = teamRepository.findByName(m.getTeamA().getName());
            Optional<Team> teamB = teamRepository.findByName(m.getTeamB().getName());
            if (teamA.isEmpty()) {
                teamRepository.save(m.getTeamA());
            }
            if (teamB.isEmpty()) {
                teamRepository.save(m.getTeamB());
            }
            teamRepository.findByName(m.getTeamA().getName()).ifPresent(m::setTeamA);
            teamRepository.findByName(m.getTeamB().getName()).ifPresent(m::setTeamB);
            setMatchStakesToRandom(m);
        });
        if (newMatches.size() > 0) {
            matchRepository.saveAll(newMatches);
            logger.info("Added new " + newMatches.size() + " matches");
        }
    }

    private void setMatchStakesToRandom(Match match) {
        float randomNum = new Random().nextFloat()
                * (Match.getMAX_STAKE() - Match.getMIN_STAKE()) + Match.getMIN_STAKE();
        match.setStakeA(randomNum);
        match.setStakeB(Match.getMAX_STAKE() - randomNum + Match.getMIN_STAKE());
    }
}
