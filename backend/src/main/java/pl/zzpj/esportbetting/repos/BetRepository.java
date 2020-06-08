package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findAllByMatch(Match match);
    List<Bet> findAllByUser(User user);
}
