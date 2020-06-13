package pl.zzpj.esportbetting.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.DetailedFinishedStatusEnum;
import pl.zzpj.esportbetting.enumerate.MatchStatusEnum;
import pl.zzpj.esportbetting.exception.AlreadyTakenException;
import pl.zzpj.esportbetting.exception.IllegalActionException;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.exception.ValidationException;
import pl.zzpj.esportbetting.interfaces.BetService;
import pl.zzpj.esportbetting.interfaces.CommentService;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.interfaces.UserLevelService;
import pl.zzpj.esportbetting.model.Bet;
import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.repos.BetRepository;
import pl.zzpj.esportbetting.repos.CommentRepository;
import pl.zzpj.esportbetting.repos.MatchRepository;
import pl.zzpj.esportbetting.repos.UserRepository;
import pl.zzpj.esportbetting.strategies.HappyHoursStrategy;
import pl.zzpj.esportbetting.strategies.NormalStrategy;

import java.time.LocalTime;
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
        Match fullMatch = getFullMatch(comment.getMatch());

        Comment commentToSave = Comment.builder()
                .user(fullUser)
                .match(fullMatch)
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
        if(!commentRepository.findAll().stream().
                filter(comment -> comment.getUser().equals(user)).
                allMatch(comment -> comment.getId() == commentId)){
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

    private Match getFullMatch(Match match) {
        return matchRepository.findById(match.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Not found match with id: "
                        + match.getId()));
    }
}
