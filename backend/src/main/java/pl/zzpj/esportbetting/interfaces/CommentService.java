package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.User;

import java.util.List;

public interface CommentService {
    Comment createCommentForMatch(Comment comment);

    List<Comment> findAllByMatchId(long matchId);

    void deleteOwnComment(User user, long commentId);

    void deleteCommentByAdmin(long commentId);

}
