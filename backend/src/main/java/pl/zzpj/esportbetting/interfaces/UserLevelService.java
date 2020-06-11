package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.strategies.ExpGiverStrategy;

public interface UserLevelService {

    void addExpAfterBet(User user, boolean won);

    void setExpGiverStrategy(ExpGiverStrategy expGiverStrategy);
}
