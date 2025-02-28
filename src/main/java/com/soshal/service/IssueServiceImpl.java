package com.soshal.service;




import com.soshal.modal.Issue;
import com.soshal.modal.Project;
import com.soshal.modal.User;
import com.soshal.repository.IssueRepository;
import com.soshal.repository.ProjectRepository;
import com.soshal.repository.UserRepo;

import com.soshal.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Issue createIssue(IssueRequest issueRequest, Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setTags(issueRequest.getTags());
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateIssue(Long issueId, IssueRequest updatedIssue, Long userId) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue not found"));
        issue.setTitle(updatedIssue.getTitle());
        issue.setDescription(updatedIssue.getDescription());
        issue.setStatus(updatedIssue.getStatus());
        issue.setPriority(updatedIssue.getPriority());
        issue.setDueDate(updatedIssue.getDueDate());
        issue.setTags(updatedIssue.getTags());
        return issueRepository.save(issue);
    }

    @Override
    public String deleteIssue(Long issueId, Long userId) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue not found"));
        issueRepository.delete(issue);
        return "Issue deleted successfully";
    }

    @Override
    public List<Issue> getIssuesByAssigneeId(Long assigneeId) throws Exception {
        return issueRepository.findByAssigneeId(assigneeId);
    }

    @Override
    public List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception {
        return issueRepository.searchByFilters(title, status, priority, assigneeId);
    }

    @Override
    public Optional<Issue> getIssueById(Long issueId) throws Exception {
        return issueRepository.findById(issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new Exception("Issue not found"));
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}

