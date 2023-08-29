package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Подписки", description = "Методы для управлениями подписками")
@RestController
@RequestMapping("/v1/subscriptions")
public class SubscriptionsController {

    private final SubscriptionService subscriptionService;
    private final JwtUtil jwtUtil;

    public SubscriptionsController(SubscriptionService subscriptionService, JwtUtil jwtUtil) {
        this.subscriptionService = subscriptionService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Подписаться на автора")
    @PostMapping("/subscribe/{publisherId}")
    private void subscribe(@Parameter(description = "Bearer header with JWT")
                           @RequestHeader("Authorization") String jwtHeader,
                           @Parameter(description = "Идентификатор автора")
                           @PathVariable Long publisherId) {
        subscriptionService.subscribe(jwtUtil.getUserIdByJwtHeader(jwtHeader), publisherId);
    }

    @Operation(summary = "Отписаться от автора")
    @PostMapping("/unsubscribe/{publisherId}")
    private void unsubscribe(@Parameter(description = "Bearer header with JWT")
                             @RequestHeader("Authorization") String jwtHeader,
                             @Parameter(description = "Идентификатор автора")
                             @PathVariable Long publisherId) {
        subscriptionService.unsubscribe(jwtUtil.getUserIdByJwtHeader(jwtHeader), publisherId);
    }
}
