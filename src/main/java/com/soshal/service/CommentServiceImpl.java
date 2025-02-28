package com.soshal.service;



import com.soshal.modal.Comment;
import com.soshal.modal.Issue;
import com.soshal.modal.User;
import com.soshal.repository.CommentRepository;
import com.soshal.repository.IssueRepository;
import com.soshal.repository.UserRepo;

import com.soshal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new Exception("Issue not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new Exception("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new Exception("You are not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> findCommentsByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
