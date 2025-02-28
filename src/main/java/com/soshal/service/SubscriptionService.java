package com.soshal.service;

import com.soshal.modal.Subscription;
import com.soshal.modal.PlanType;

public interface SubscriptionService {
    Subscription createSubscription(Long userId) throws Exception;

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;

    boolean isSubscriptionValid(Long userId);
}
