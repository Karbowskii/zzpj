package pl.zzpj.esportbetting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
}
