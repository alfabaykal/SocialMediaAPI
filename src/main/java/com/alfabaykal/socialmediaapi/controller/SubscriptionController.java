package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.facade.SubscriptionFacade;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Подписки", description = "Методы для управлениями подписками")
@RestController
@RequestMapping("/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionFacade subscriptionFacade;
    private final JwtUtil jwtUtil;

    public SubscriptionController(SubscriptionFacade subscriptionFacade, JwtUtil jwtUtil) {
        this.subscriptionFacade = subscriptionFacade;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Подписаться на автора")
    @PostMapping("/subscribe/{publisherId}")
    private void subscribe(@Parameter(description = "Bearer header with JWT")
                           @RequestHeader("Authorization") String jwtHeader,
                           @Parameter(description = "Идентификатор автора")
                           @PathVariable Long publisherId) {
        subscriptionFacade.subscribe(jwtUtil.getUserIdByJwtHeader(jwtHeader), publisherId);
    }

    @Operation(summary = "Отписаться от автора")
    @PostMapping("/unsubscribe/{publisherId}")
    private void unsubscribe(@Parameter(description = "Bearer header with JWT")
                             @RequestHeader("Authorization") String jwtHeader,
                             @Parameter(description = "Идентификатор автора")
                             @PathVariable Long publisherId) {
        subscriptionFacade.unsubscribe(jwtUtil.getUserIdByJwtHeader(jwtHeader), publisherId);
    }
}
