package com.soshal.service;

import jakarta.mail.MessagingException;

public interface Emailservice {

   void sendInvitation(String email, String token) throws MessagingException;
}
