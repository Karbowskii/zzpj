package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
}
