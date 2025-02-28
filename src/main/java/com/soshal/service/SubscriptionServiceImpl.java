package com.soshal.service;

import com.soshal.modal.Subscription;
import com.soshal.modal.PlanType;
import com.soshal.modal.User;
import com.soshal.repository.SubscriptionRepository;
import com.soshal.repository.UserRepo;

import com.soshal.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public Subscription createSubscription(Long userId) throws Exception {
        if (subscriptionRepository.findByUserId(userId) != null) {
            throw new Exception("User already has a subscription");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(12));
        subscription.setPlanType(PlanType.FREE);
        subscription.setValid(true);

        return subscriptionRepository.save(subscription);
    }


    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) throw new Exception("Subscription not found");

        if (!isSubscriptionValid(userId)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(12));
            subscription.setValid(true);
            subscriptionRepository.save(subscription);
        }
        return subscription;
    }


    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) throw new Exception("Subscription not found");

        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());

        if (planType == PlanType.ANNUALLY) {
            subscription.setEndDate(LocalDate.now().plusMonths(12));
        } else if (planType == PlanType.MONTHLY) {
            subscription.setEndDate(LocalDate.now().plusMonths(1));
        }

        subscription.setValid(true);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isSubscriptionValid(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) return false;

        LocalDate endDate = subscription.getEndDate();
        return endDate.isAfter(LocalDate.now()) || endDate.isEqual(LocalDate.now());
    }
}
