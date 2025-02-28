package com.soshal.controller;

import com.soshal.modal.Invitation;
import com.soshal.modal.User;
import com.soshal.service.InvitationService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendInvitation(
            @RequestParam String email,
            @RequestParam Long projectId,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        invitationService.sendInvitation(email, projectId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/accept")
    public ResponseEntity<Invitation> acceptInvitation(
            @RequestParam String token,
            @RequestHeader("Authorization") String userToken) throws Exception {

        String jwt = userToken.substring(7);
        User invitee = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(invitationService.acceptInvitation(token, invitee.getId()));
    }

    @GetMapping("/get-token")
    public ResponseEntity<String> getTokenByUserMail(@RequestParam String email) {
        return ResponseEntity.ok(invitationService.getTokenByUserMail(email));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteToken(@RequestParam String token) {
        invitationService.deleteToken(token);
        return ResponseEntity.ok().build();
    }
}
