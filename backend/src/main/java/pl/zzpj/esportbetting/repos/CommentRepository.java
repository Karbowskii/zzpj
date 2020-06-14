package pl.zzpj.esportbetting.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMatchId(long matchId);

    Optional<Comment> findById(long id);
}
