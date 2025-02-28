package com.soshal.controller;

import com.soshal.modal.Comment;
import com.soshal.modal.User;
import com.soshal.service.CommentService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(
            @RequestParam Long issueId,
            @RequestParam String content,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(commentService.createComment(issueId, user.getId(), content));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) {
        return ResponseEntity.ok(commentService.findCommentsByIssueId(issueId));
    }
}
