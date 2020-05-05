package pl.zzpj.esportbetting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.model.Authority;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityEnum name);
}
