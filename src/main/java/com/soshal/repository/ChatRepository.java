package com.soshal.repository;



import com.soshal.modal.Chat;
import com.soshal.modal.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByProject(Project project);
}