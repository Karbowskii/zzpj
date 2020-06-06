package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.User;

public interface UserLevelService {
    void addExpAfterBet(User user, boolean won);
}
