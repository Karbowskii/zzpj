package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.Match;

public interface BetService {

    void withdrawBetMoneyAndExpForMatch(Match match);

    void returnCoinsIfMatchCanceled(Match match);

}
