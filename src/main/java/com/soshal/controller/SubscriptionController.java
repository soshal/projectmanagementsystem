package com.soshal.controller;

import com.soshal.modal.Subscription;
import com.soshal.modal.User;
import com.soshal.modal.PlanType;
import com.soshal.service.SubscriptionService;
import com.soshal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(subscriptionService.getUserSubscription(user.getId()));
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestParam PlanType planType,
            @RequestHeader("Authorization") String token) throws Exception {

        String jwt = token.substring(7);
        User user = userService.findUserProfileByJwt(jwt);

        return ResponseEntity.ok(subscriptionService.upgradeSubscription(user.getId(), planType));
    }
}
