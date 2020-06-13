package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.model.Match;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findAllByStatusNotLike(MatchStatusEnum status);
    Optional<Match> findByRealId(int realId);
    List<Match> findTop5ByStatusIsOrderByStartDate(MatchStatusEnum status);
}
