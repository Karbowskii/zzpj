package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;

import java.util.List;

public interface BetService {

    void withdrawBetMoneyAndExpForMatch(Match match);

    void returnCoinsIfMatchCanceled(Match match);

}
