package com.soshal.controller;

import com.soshal.config.JwtProvider;
import com.soshal.modal.Invitation;
import com.soshal.modal.Project;
import com.soshal.modal.User;
import com.soshal.modal.Chat;
import com.soshal.request.InviteReq;
import com.soshal.response.MessageResponse;
import com.soshal.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private Emailservice emailservice;


    @Autowired
    private InvitationService invitationService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7); // Remove "Bearer " prefix
        User user = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(projectService.createProject(project, user));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Project>> getProjectsByUser(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(projectService.getProjectsByUser(user, category, tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt); // Ensure user is authenticated
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(id, user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @RequestBody Project project,
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(projectService.updateProject(project, id));
    }

    @PostMapping("/{projectId}/add-user")
    public ResponseEntity<Void> addUserToProject(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);
        projectService.addUserToProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/remove-user")
    public ResponseEntity<Void> removeUserFromProject(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);
        projectService.removeUserFromProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(projectService.getChatByProjectId(projectId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);
        return ResponseEntity.ok(projectService.searchProjects(keyword, user));
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> sendInvitation(
            @RequestBody InviteReq req,
            @RequestParam Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {

        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accept")
    public ResponseEntity<Invitation> acceptInvitation(
            @RequestParam String link,
            @RequestParam Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        Invitation invitation = invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());

        MessageResponse res = new MessageResponse(("User Invitation send "));



        return ResponseEntity.ok().build();
    }
}
