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
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.ESportRestApi;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.Team;
import pl.zzpj.esportbetting.model.parsers.MatchApiParser;
import pl.zzpj.esportbetting.repos.BetRepository;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.repos.TeamRepository;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.interfaces.MatchService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("matchService")
@EnableScheduling
public class MatchServiceImpl implements MatchService {
    private static final GameEnum GAME_NAME = GameEnum.LEAGUE_OF_LEGENDS;
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchServiceImpl.class);

    private final MatchRepository matchRepository;
    private final BetRepository betRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ESportRestApi eSportRestApi;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, ESportRestApi eSportRestApi,
                            BetRepository betRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.eSportRestApi = eSportRestApi;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Scheduled(cron = "1 * * * * ?")
    public void checkAndUpdateMatches() throws IOException, InterruptedException, JSONException {
        List<Match> matchesFromApi = MatchApiParser.parse(eSportRestApi.getAllMatches(GAME_NAME));
        findNewMatchesAndAndToDB(matchesFromApi);
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
                                .map(api -> {
                                    api.setId(match.getId());
                                    api.setTeamA(match.getTeamA());
                                    api.setTeamB(match.getTeamB());
                                    return api;
                                })
                                .collect(Collectors.toList())));
        matchRepository.saveAll(matchesToChange);

        List<Match> matchesToDelete = new ArrayList<>();
        matchesToChange.stream().filter(m -> m.getStatus().equals(MatchStatusEnum.CANCELED))
                .collect(Collectors.toList())
                .forEach(match -> {
                    returnCoinsIfMatchCanceled(match);
                    matchesToDelete.add(match);
                });
        matchRepository.deleteAll(matchesToDelete);

        LOGGER.info("Finished updating " + matchesToChange.size() + " matches");
    }

    public void returnCoinsIfMatchCanceled(Match match) {
        betRepository.findAllByMatch(match).forEach(b -> {
            LOGGER.info("Canceled match - Returning bet coins of user with username: " + b.getUser().getUsername());
            userRepository.findById(b.getUser().getId())
                    .orElseThrow(() ->
                            new ObjectNotFoundException("User with id: " + b.getUser().getId() + " not exists!"))
                    .addCoins(b.getCoins());
            LOGGER.info("Returned " + b.getCoins() + " coins to user with username " + b.getUser().getUsername());
        });
    }

    public void findNewMatchesAndAndToDB(List<Match> allMatchesFromApi) {
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
        });
        if (newMatches.size() > 0) {
            matchRepository.saveAll(newMatches);
            LOGGER.info("Added new " + newMatches.size() + " matches");
        }
    }
}
