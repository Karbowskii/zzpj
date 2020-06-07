package pl.zzpj.esportbetting.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.DetailedFinishedStatusEnum;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.interfaces.UserLevelService;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.BetRepository;
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

    @Autowired
    public BetServiceImpl(UserRepository userRepository, BetRepository betRepository,
                          UserLevelService userLevelService) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.userLevelService = userLevelService;
    }

    @Override
    public void withdrawBetMoneyAndExpForMatch(Match match) {
        List<Bet> bets = betRepository.findAllByMatch(match);
        bets.forEach(b -> {
            User user = b.getUser();
            int userStake = b.isSelectedA() ? match.getStakeA() : match.getStakeB();
            boolean userWon = checkIfUserWon(b);
            if (userWon) {
                user.addCoins(b.getCoins() * userStake);
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
}
