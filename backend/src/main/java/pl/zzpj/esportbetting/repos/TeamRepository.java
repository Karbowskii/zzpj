package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
