package pl.zzpj.esportbetting.rest;

import org.hibernate.annotations.SQLDeleteAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.esportbetting.interfaces.CommentService;
import pl.zzpj.esportbetting.interfaces.MatchService;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.model.Comment;
import pl.zzpj.esportbetting.model.Match;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.request.CreateCommentRequest;

import java.util.List;

@RestController
@RequestMapping(path = "comments")
public class CommentRestController {

    private final CommentService commentService;
    private final MatchService matchService;
    private final UserContextService userContextService;

    @Autowired
    public CommentRestController(CommentService commentService, MatchService matchService, UserContextService userContextService) {
        this.commentService = commentService;
        this.matchService = matchService;
        this.userContextService = userContextService;
    }

    @PostMapping
    public ResponseEntity<Comment> makeComment(@RequestBody CreateCommentRequest createCommentRequest) {
        User user = userContextService.getAuthenticatedUser();
        Match match = matchService.findById(createCommentRequest.getMatchId());
        Comment comment = Comment.builder()
                .match(match)
                .user(user)
                .text(createCommentRequest.getText())
                .build();
        Comment createdComment = commentService.createCommentForMatch(comment);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping(path = "/match/{id}")
    public ResponseEntity<List<Comment>> findAllByMatchId(@PathVariable("id") long id){
        return ResponseEntity.ok(commentService.findAllByMatchId(id));
    }

    @DeleteMapping(path ="/{id}")
    public void deleteOwnComment(@PathVariable("id") long id){
        User user = userContextService.getAuthenticatedUser();
        commentService.deleteOwnComment(user, id);
    }

    @DeleteMapping(path ="/{id}")
    public void deleteCommentByAdmin(@PathVariable("id") long id){
        commentService.deleteCommentByAdmin(id);
    }
}
