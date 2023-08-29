package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Дружба", description = "Методы для добавления и удаления друзей")
@RestController
@RequestMapping("/v1/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final JwtUtil jwtUtil;

    public FriendshipController(FriendshipService friendshipService, JwtUtil jwtUtil) {
        this.friendshipService = friendshipService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Отправка запроса на добавление в друзья")
    @PostMapping("/request/{receiverId}")
    public void sendFriendRequest(@Parameter(description = "Bearer header with JWT")
                                  @RequestHeader("Authorization") String jwtHeader,
                                  @Parameter(description = "Идентификатор получателя")
                                  @PathVariable Long receiverId) {
        friendshipService.sendFriendRequest(jwtUtil.getUserIdByJwtHeader(jwtHeader), receiverId);
    }

    @Operation(summary = "Принятие запроса на добавление в друзья")
    @PatchMapping("/accept/{senderId}")
    public void acceptFriendRequest(@Parameter(description = "Bearer header with JWT")
                                    @RequestHeader("Authorization") String jwtHeader,
                                    @Parameter(description = "Идентификатор отправителя")
                                    @PathVariable Long senderId) {
        friendshipService.acceptFriendRequest(senderId, jwtUtil.getUserIdByJwtHeader(jwtHeader));
    }

    @Operation(summary = "Отклонение запроса на добавление в друзья")
    @PatchMapping("/decline/{senderId}")
    public void declineFriendRequest(@Parameter(description = "Bearer header with JWT")
                                     @RequestHeader("Authorization") String jwtHeader,
                                     @Parameter(description = "Идентификатор отправителя")
                                     @PathVariable Long senderId) {
        friendshipService.declineFriendRequest(senderId, jwtUtil.getUserIdByJwtHeader(jwtHeader));
    }

    @Operation(summary = "Удаление пользователя из списка друзей")
    @DeleteMapping("/remove/{senderId}")
    public void removeFriend(@Parameter(description = "Bearer header with JWT")
                             @RequestHeader("Authorization") String jwtHeader,
                             @Parameter(description = "Идентификатор отправителя")
                             @PathVariable Long senderId) {
        friendshipService.removeFriend(jwtUtil.getUserIdByJwtHeader(jwtHeader), senderId);
    }

}
