package com.soshal.service;


import com.soshal.modal.Issue;
import com.soshal.modal.User;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue createIssue(IssueRequest issue, Long userId) throws Exception;

    Issue updateIssue(Long issueId, IssueRequest updatedIssue, Long userId) throws Exception;

    String deleteIssue(Long issueId, Long userId) throws Exception;

    List<Issue> getIssuesByAssigneeId(Long assigneeId) throws Exception;

    List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception;

    Optional<Issue> getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;
}
