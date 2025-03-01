package com.soshal.service;

import com.soshal.modal.Chat;
import com.soshal.modal.Project;
import com.soshal.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    @Lazy
    private ChatRepository chatRepository;

    @Autowired
    @Lazy
    private ProjectService projectService;

    @Autowired
    @Lazy
    public ChatServiceImpl(ChatRepository chatRepository, ProjectService projectService) {
        this.chatRepository = chatRepository;
        this.projectService = projectService; // Inject the interface
    }

    @Override
    public Chat createChat(Long projectId) throws Exception {
        Project project = projectService.getProjectById(projectId);
        Chat chat = new Chat();
        chat.setProject(project);
        project.setChat(chat);
        return chatRepository.save(chat);
    }

    @Override
    public Chat createChat(Chat projectId) throws Exception {
        return null;
    }

    @Override
    public Chat createChat(Project project) {
        Chat chat = new Chat();
        chat.setProject(project);
        return chatRepository.save(chat);
    }








    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return project.getChat();
    }
}
