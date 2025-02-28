package com.soshal.repository;

import com.soshal.modal.Message;
import com.soshal.modal.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChat(Chat chat);
}
