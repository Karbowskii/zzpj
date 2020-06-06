package pl.zzpj.esportbetting.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.interfaces.UserLevelService;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.LevelRepository;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.strategies.ExpGiverStrategy;
import pl.zzpj.esportbetting.strategies.HappyHoursStrategy;
import pl.zzpj.esportbetting.strategies.NormalStrategy;

import java.time.LocalTime;

@Service("userLevelService")
public class UserLevelServiceImpl implements UserLevelService {

    private static final int EXP_AFTER_WINNING_BET = 200;
    private static final int EXP_AFTER_LOOSING_BET = 50;

    @Value("#{T(java.time.LocalTime).parse('${esportbetting.app.happy-hours.from}')}")
    private LocalTime happyHoursFrom;

    @Value("#{T(java.time.LocalTime).parse('${esportbetting.app.happy-hours.to}')}")
    private LocalTime happyHoursTo;

    private final LevelRepository levelRepository;
    private final UserRepository userRepository;
    private ExpGiverStrategy expGiverStrategy;

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
        if (checkIfHappyHourNow()) {
            user.addExp(new HappyHoursStrategy().calcExp(expToAdd));
        } else {
            user.addExp(new NormalStrategy().calcExp(expToAdd));
        }
        while (checkIfReachedNextLevel(user)) {
            levelRepository.findById(user.getLevel().getId() + 1)
                    .ifPresent(user::levelUp);
        }
    }

    public boolean checkIfReachedNextLevel(User user) {
        return user.getExp() >= user.getLevel().getExpToNextLevel();
    }

    public boolean checkIfHappyHourNow() {
        LocalTime timeNow = LocalTime.now();
        return timeNow.isAfter(happyHoursFrom) && timeNow.isBefore(happyHoursTo);
    }
}
