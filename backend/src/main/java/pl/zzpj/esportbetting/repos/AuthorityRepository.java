package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
