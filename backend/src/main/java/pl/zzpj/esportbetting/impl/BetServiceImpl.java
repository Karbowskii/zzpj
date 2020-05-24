package pl.zzpj.esportbetting.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.BetRepository;
import pl.zzpj.esportbetting.repos.UserRepository;

import java.util.List;

@Service("betService")
public class BetServiceImpl implements BetService {

    private final UserRepository userRepository;
    private final BetRepository betRepository;
    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);

    @Autowired
    public BetServiceImpl(UserRepository userRepository, BetRepository betRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
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
            user.addExpAfterBet(userWon);
        });
    }

    public boolean checkIfUserWon(Bet bet) {
        boolean userSelectedA = bet.isSelectedA();
        Match match = bet.getMatch();
        return match.getWhichTeamWon() == 1 && userSelectedA    // A won
            || match.getWhichTeamWon() == 0                     // draw
            || match.getWhichTeamWon() == -1 && !userSelectedA; // B won
    }

    @Override
    public void returnCoinsIfMatchCanceled(Match match) {
        betRepository.findAllByMatch(match).forEach(b -> {
            logger.info("Canceled match - Returning bet coins of user with username: " + b.getUser().getUsername());
            userRepository.findById(b.getUser().getId())
                    .orElseThrow(() ->
                            new ObjectNotFoundException("User with id: " + b.getUser().getId() + " not exists!"))
                    .addCoins(b.getCoins());
            logger.info("Returned " + b.getCoins() + " coins to user with username " + b.getUser().getUsername());
        });
    }
}
