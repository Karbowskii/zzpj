package pl.zzpj.esportbetting.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.exception.IllegalActionException;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.CommentService;
import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.CommentRepository;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.repos.UserRepository;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, CommentRepository commentRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public Comment createCommentForMatch(Comment comment) {
        User fullUser = getFullUser(comment.getUser());

        Comment commentToSave = Comment.builder()
                .user(fullUser)
                .match(comment.getMatch())
                .text(comment.getText())
                .build();
        userRepository.saveAndFlush(fullUser);
        return commentRepository.saveAndFlush(commentToSave);
    }

    @Override
    public List<Comment> findAllByMatchId(long matchId) {
        return commentRepository.findAllByMatchId(matchId);
    }

    @Override
    public void deleteOwnComment(User user, long commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new ObjectNotFoundException("There is no commennt with id: " + commentId));
        if(!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalActionException("Cannot delete not yours comment!!!");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteCommentByAdmin(long commentId) {
        if(!commentRepository.existsById(commentId)){
            throw new IllegalActionException("There is no comment with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    private User getFullUser(User user) {
        return userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("Not found user with username: "
                        + user.getUsername()));
    }
}
