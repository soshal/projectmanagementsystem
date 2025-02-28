package com.soshal.repository;

import com.soshal.modal.Project;
import com.soshal.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // ✅ Fix: Use MEMBER OF for List<User> collections in JPQL queries
    @Query("SELECT p FROM Project p WHERE :user MEMBER OF p.team OR p.owner = :user")
    List<Project> findProjectsForUser(@Param("user") User user);

    // ✅ Fixed search query
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND (:user MEMBER OF p.team OR p.owner = :user)")
    List<Project> searchProjects(@Param("keyword") String keyword, @Param("user") User user);
}
