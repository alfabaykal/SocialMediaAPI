package com.alfabaykal.socialmediaapi.facade;

public interface SubscriptionFacade {

    void subscribe(Long subscriberId, Long publisherId);

    void unsubscribe(Long subscriberId, Long publisherId);

}
