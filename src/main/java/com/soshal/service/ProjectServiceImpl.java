package com.soshal.service;

import com.soshal.modal.Project;
import com.soshal.modal.User;
import com.soshal.modal.Chat;
import com.soshal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ChatServiceImpl chatService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        project.setOwner(user);
        Project saved = projectRepository.save(project);

        Chat chat = new Chat();
        chat.setProject(saved);
        Chat projectChat = chatService.createChat(chat);

        saved.setChat(projectChat);
        return projectRepository.save(saved);
    }

    @Override
    public List<Project> getProjectsByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findProjectsForUser(user);

        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }

        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public List<Project> getProjectsByUser(User user, String category, String tag) throws Exception {
        return projectRepository.findProjectsForUser(user);
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found"));
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new Exception("You are not authorized to delete this project");
        }
        projectRepository.delete(project);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project existingProject = getProjectById(id);
        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setCategory(updatedProject.getCategory());
        existingProject.setTags(updatedProject.getTags());
        return projectRepository.save(existingProject);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        project.getTeam().removeIf(user -> user.getId().equals(userId));
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        return projectRepository.searchProjects(keyword, user);
    }
}
