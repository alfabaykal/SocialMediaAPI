package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.facade.SubscriptionFacade;
import com.alfabaykal.socialmediaapi.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionFacadeImpl implements SubscriptionFacade {

    private final SubscriptionService subscriptionService;

    public SubscriptionFacadeImpl(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void subscribe(Long subscriberId, Long publisherId) {
        subscriptionService.subscribe(subscriberId, publisherId);
    }

    @Override
    public void unsubscribe(Long subscriberId, Long publisherId) {
        subscriptionService.unsubscribe(subscriberId, publisherId);
    }
}
