package com.soshal.controller;



import com.soshal.modal.Message;
import com.soshal.modal.User;
import com.soshal.service.MessageService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestParam Long chatId,
            @RequestParam String content,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User sender = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(messageService.sendMessage(chatId, sender, content));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long chatId) throws Exception {
        return ResponseEntity.ok(messageService.getMessagesByChatId(chatId));
    }
}
