package pl.zzpj.esportbetting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
