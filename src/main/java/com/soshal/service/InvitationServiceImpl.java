package com.soshal.service;

import com.soshal.modal.Invitation;
import com.soshal.modal.Project;
import com.soshal.modal.User;
import com.soshal.repository.InvitationRepository;
import com.soshal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private Emailservice emailService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;





    @Override
    public void sendInvitation(String email, Long projectId) throws Exception {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found"));

        // Generate a unique invitation token
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setToken(invitationToken);
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitationRepository.save(invitation);

        // Send email with invitation link
        String invitationLink = "http://localhost:3000/accept_invitation?token=" + invitationToken;
        emailService.sendInvitation(email, invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String invitationToken, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(invitationToken);
        if (invitation == null) {
            throw new Exception("Invalid invitation token");
        }

        Project project = projectRepository.findById(invitation.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));



        // Remove invitation from the database
        invitationRepository.delete(invitation);

        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return (invitation != null) ? invitation.getToken() : null;
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        if (invitation != null) {
            invitationRepository.delete(invitation);
        }
    }
}
