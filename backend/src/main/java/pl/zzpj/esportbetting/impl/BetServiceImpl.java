package pl.zzpj.esportbetting.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.DetailedFinishedStatusEnum;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.exception.AlreadyTakenException;
import pl.zzpj.esportbetting.exception.IllegalActionException;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.exception.ValidationException;
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.interfaces.UserLevelService;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.BetRepository;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.strategies.HappyHoursStrategy;
import pl.zzpj.esportbetting.strategies.NormalStrategy;

import java.time.LocalTime;
import java.util.List;

@Service("betService")
public class BetServiceImpl implements BetService {

    @Value("#{T(java.time.LocalTime).parse('${esportbetting.app.happy-hours.from}')}")
    private LocalTime happyHoursFrom;

    @Value("#{T(java.time.LocalTime).parse('${esportbetting.app.happy-hours.to}')}")
    private LocalTime happyHoursTo;

    private final UserRepository userRepository;
    private final BetRepository betRepository;
    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);
    private final UserLevelService userLevelService;
    private final MatchRepository matchRepository;

    @Autowired
    public BetServiceImpl(UserRepository userRepository, BetRepository betRepository, UserLevelService userLevelService, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.userLevelService = userLevelService;
        this.matchRepository = matchRepository;
    }

    @Override
    public void withdrawBetMoneyAndExpForMatch(Match match) {
        List<Bet> bets = betRepository.findAllByMatch(match);
        bets.forEach(b -> {
            User user = b.getUser();
            float userStake = b.isSelectedA() ? match.getStakeA() : match.getStakeB();
            boolean userWon = checkIfUserWon(b);
            if (userWon) {
                int coinsToAdd = Math.round(b.getCoins() * userStake);
                user.addCoins(coinsToAdd);
                logger.info("Withdraw " + coinsToAdd + " coins for user " + user.getUsername());
            }
            if (checkIfHappyHourNow()) {
                userLevelService.setExpGiverStrategy(new HappyHoursStrategy());
            } else {
                userLevelService.setExpGiverStrategy(new NormalStrategy());
            }
            userLevelService.addExpAfterBet(user, userWon);
        });
    }

    public boolean checkIfUserWon(Bet bet) {
        boolean userSelectedA = bet.isSelectedA();
        Match match = bet.getMatch();
        return match.getWhichTeamWon() == DetailedFinishedStatusEnum.A_WIN && userSelectedA
            || match.getWhichTeamWon() == DetailedFinishedStatusEnum.DRAW
            || match.getWhichTeamWon() == DetailedFinishedStatusEnum.B_WIN && !userSelectedA;
    }

    private boolean checkIfHappyHourNow() {
        LocalTime timeNow = LocalTime.now();
        return timeNow.isAfter(happyHoursFrom) && timeNow.isBefore(happyHoursTo);
    }

    @Override
    public void returnCoinsIfMatchCanceled(Match match) {
        betRepository.findAllByMatch(match).forEach(b -> {
            logger.info("Canceled match - Returning bet coins of user with username: " + b.getUser().getUsername());
            User user = userRepository.findById(b.getUser().getId())
                    .orElseThrow(() ->
                            new ObjectNotFoundException("User with id: " + b.getUser().getId() + " not exists!"));
            user.addCoins(b.getCoins());
            userRepository.saveAndFlush(user);
            logger.info("Returned " + b.getCoins() + " coins to user with username " + b.getUser().getUsername());
        });
    }

    @Override
    public Bet createBetForUser(User userFromContext, Bet bet) {
        User fullUser = getFullUser(userFromContext);
        Match match = bet.getMatch();
        if (!match.getStatus().equals(MatchStatusEnum.NOT_STARTED)
                && !match.getStatus().equals(MatchStatusEnum.POSTPONED)){
            throw new IllegalActionException("Cannot create bet for " + match.getStatus().toString() + " match!!!");
        }
        if (bet.getCoins() <= 0) {
            throw new ValidationException("Bet coins must be greater than zero!!!");
        }
        List<Bet> allUserBets = betRepository.findAllByUser(fullUser);
        if (allUserBets.stream().anyMatch(b -> b.getMatch().getId() == bet.getMatch().getId())) {
            throw new AlreadyTakenException("User can only have one bet per match!!!");
        }
        if (countUserRunningMatches(allUserBets) >= fullUser.getLevel().getMaxBets()) {
            throw new IllegalActionException("User reached maximum number of active bets!!!");
        }
        if (bet.getCoins() > fullUser.getCoins()) {
            throw new IllegalActionException("Not enough coins to make bet!!!");
        }
        Bet betToSave = Bet.builder()
                .user(fullUser)
                .match(match)
                .coins(bet.getCoins())
                .selectedA(bet.isSelectedA())
                .build();
        fullUser.removeCoins(betToSave.getCoins());
        userRepository.saveAndFlush(fullUser);
        return betRepository.saveAndFlush(betToSave);
    }

    private User getFullUser(User user) {
        return userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new ObjectNotFoundException("Not found user with username: "
                            + user.getUsername()));
    }

    private long countUserRunningMatches(List<Bet> allUserBets) {
        return allUserBets.stream()
                .filter(b -> !b.getMatch().getStatus().equals(MatchStatusEnum.FINISHED))
                .count();
    }

    @Override
    public List<Bet> getAllBetsForUser(User user) {
        User fullUser = getFullUser(user);
        return betRepository.findAllByUser(fullUser);

    public List<Bet> findAllByUserId(long userId) {
        return this.betRepository.findAllByUserId(userId);

    }
}
