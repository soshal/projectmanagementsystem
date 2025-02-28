package com.soshal.controller;



import com.soshal.modal.Chat;
import com.soshal.modal.Project;
import com.soshal.modal.User;
import com.soshal.repository.ProjectRepository;
import com.soshal.service.ChatService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/{projectId}")
    public ResponseEntity<Chat> getChatByProjectId(@PathVariable Long projectId,
                                                   @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found"));

        if (!project.getTeam().contains(user)) {
            throw new Exception("Access Denied: You are not part of this project");
        }

        return ResponseEntity.ok(chatService.getChatByProjectId(projectId));
    }

}
