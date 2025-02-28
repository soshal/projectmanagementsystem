package com.soshal.controller;


import com.soshal.modal.Issue;
import com.soshal.modal.User;
import com.soshal.service.IssueRequest;
import com.soshal.service.IssueService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    // ✅ Create Issue with Authentication
    @PostMapping
    public ResponseEntity<Issue> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7); // Remove "Bearer "
        User user = userService.findUserProfileByJwt(jwt);

        Issue createdIssue = issueService.createIssue(issue, user.getId());
        return ResponseEntity.ok(createdIssue);
    }

    // ✅ Update Issue with Authentication
    @PutMapping("/{issueId}")
    public ResponseEntity<Issue> updateIssue(
            @PathVariable Long issueId,
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        Issue updatedIssue = issueService.updateIssue(issueId, issue, user.getId());
        return ResponseEntity.ok(updatedIssue);
    }

    // ✅ Delete Issue with Authentication
    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        String response = issueService.deleteIssue(issueId, user.getId());
        return ResponseEntity.ok(response);
    }

    // ✅ Get Issues Assigned to User
    @GetMapping("/assignee")
    public ResponseEntity<List<Issue>> getIssuesByAssignee(
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        List<Issue> issues = issueService.getIssuesByAssigneeId(user.getId());
        return ResponseEntity.ok(issues);
    }

    // ✅ Search Issues (Admin or Assignee)
    @GetMapping("/search")
    public ResponseEntity<List<Issue>> searchIssues(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        List<Issue> issues = issueService.searchIssues(title, status, priority, user.getId());
        return ResponseEntity.ok(issues);
    }

    // ✅ Get Issue by ID
    @GetMapping("/{issueId}")
    public ResponseEntity<Optional<Issue>> getIssueById(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);

        Optional<Issue> issue = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issue);
    }

    // ✅ Get Issues by Project ID
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);

        List<Issue> issues = issueService.getIssueByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }

    // ✅ Add User to Issue
    @PostMapping("/{issueId}/assign/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);

        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    // ✅ Update Issue Status
    @PatchMapping("/{issueId}/status")
    public ResponseEntity<Issue> updateStatus(
            @PathVariable Long issueId,
            @RequestParam String status,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);

        Issue updatedIssue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(updatedIssue);
    }
}
