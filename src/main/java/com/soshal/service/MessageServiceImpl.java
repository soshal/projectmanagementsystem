package com.soshal.service;



import com.soshal.modal.Chat;
import com.soshal.modal.Message;
import com.soshal.modal.User;
import com.soshal.repository.ChatRepository;
import com.soshal.repository.MessageRepository;
import com.soshal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message sendMessage(Long chatId, User sender, String content) throws Exception {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new Exception("Chat not found"));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByChatId(Long chatId) throws Exception {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new Exception("Chat not found"));

        return messageRepository.findByChat(chat);
    }
}
