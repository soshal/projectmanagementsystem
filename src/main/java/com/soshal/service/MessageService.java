package com.soshal.service;



import com.soshal.modal.Message;
import com.soshal.modal.User;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long chatId, User sender, String content) throws Exception;

    List<Message> getMessagesByChatId(Long chatId) throws Exception;
}
