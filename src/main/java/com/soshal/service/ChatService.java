package com.soshal.service;



import com.soshal.modal.Chat;
import com.soshal.modal.Project;

public interface ChatService {
    Chat createChat(Long projectId) throws Exception;

    Chat createChat(Project project);


    Chat createChat(Chat projectId) throws Exception;
    Chat getChatByProjectId(Long projectId) throws Exception;



}
