package com.soshal.service;


import com.soshal.modal.Invitation;

import java.util.Optional;

public interface InvitationService {

    void sendInvitation(String email, Long projectid) throws Exception;

    Invitation acceptInvitation(String invitationToken, Long userId) throws Exception;

    String getTokenByUserMail(String userEmail);

    void deleteToken(String token);
}
