package pl.zzpj.esportbetting.impl;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.interfaces.UserLevelService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.LevelRepository;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.strategies.ExpGiverStrategy;
import pl.zzpj.esportbetting.strategies.NormalStrategy;

@Service("userLevelService")
public class UserLevelServiceImpl implements UserLevelService {

    private static final int EXP_AFTER_WINNING_BET = 200;
    private static final int EXP_AFTER_LOOSING_BET = 50;

    private static final Logger logger = LoggerFactory.getLogger(UserLevelServiceImpl.class);
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;
    @Setter @Getter
    private ExpGiverStrategy expGiverStrategy = new NormalStrategy();

    @Autowired
    UserLevelServiceImpl(LevelRepository levelRepository, UserRepository userRepository) {
        this.levelRepository = levelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addExpAfterBet(User user, boolean won) {
        int expToAdd = 0;
        if (won) {
            expToAdd = EXP_AFTER_WINNING_BET;
        } else {
            expToAdd = EXP_AFTER_LOOSING_BET;
        }
        int expAfterStrategy = expGiverStrategy.calcExp(expToAdd);
        user.addExp(expAfterStrategy);
        logger.info("Withdraw " + expAfterStrategy + " exp for user " + user.getUsername());
        while (checkIfReachedNextLevel(user)) {
            levelRepository.findById(user.getLevel().getId() + 1)
                    .ifPresent((level) -> {
                        user.levelUp(level);
                        logger.info("Level up user " + user.getUsername() + " to level " + level.getId());
                    });
        }
        userRepository.saveAndFlush(user);
    }

    private boolean checkIfReachedNextLevel(User user) {
        return user.getExp() >= user.getLevel().getExpToNextLevel();
    }
}
