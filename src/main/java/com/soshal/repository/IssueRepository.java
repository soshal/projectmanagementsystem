package com.soshal.repository;



import com.soshal.modal.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);

    List<Issue> findByAssigneeId(Long assigneeId);

    @Query("SELECT i FROM Issue i WHERE " +
            "(:title IS NULL OR i.title LIKE %:title%) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:priority IS NULL OR i.priority = :priority) " +
            "AND (:assigneeId IS NULL OR i.assignee.id = :assigneeId)")
    List<Issue> searchByFilters(String title, String status, String priority, Long assigneeId);
}
